package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.repository.entities.BotUser;
import ru.nxthing.repository.entities.Word;
import ru.nxthing.repository.entities.WordCollection;
import ru.nxthing.service.BotUserService;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GetQuestionCommand extends AbstarctBotCommand {
    private static final String commandIdentifier = "get_question";
    private static final String description = "Получить вопрос";
    private static final String NO_SUBSCRIBES_MESSAGE = "У вас нет добавленных коллекций. Используйте <b>/show_collections</b> для вывода доступных коллекций. Используйте <b>/help</b> для вывода большей информации";


    private final BotUserService botUserService;

    public GetQuestionCommand(BotUserService botUserService) {
        super(commandIdentifier, description);
        this.botUserService = botUserService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
        BotUser botUser = botUserService.findByChatId(chat.getId()).get();

        Set<WordCollection> subscribes = botUser.getSubscribedCollections();

        if (subscribes.isEmpty()) {
            try {
                absSender.execute(SendMessage.builder()
                        .chatId(chat.getId().toString())
                        .text(NO_SUBSCRIBES_MESSAGE)
                        .parseMode("HTML")
                        .build());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            List<Word> words = botUser.getSubscribedCollections().stream()
                    .flatMap(collection -> collection.getWords().stream())
                    .collect(Collectors.toList());

            Random random = new Random();

            int questionIndex = random.nextInt(words.size());

            Word questionWord = words.get(questionIndex);
            List<Word> translations = questionWord.getTranslations();
            Word answerWord = translations.get(random.nextInt(translations.size()));

            String question = questionWord.getWord();
            String answer = answerWord.getWord();

            List<String> options = new ArrayList<>(4);
            options.add(answer);
            for (int i = 1; i <= 3; i++) {
                int index;
                while ((index = random.nextInt(words.size())) == questionIndex) {
                    index = random.nextInt(words.size());
                }
                List<Word> optionTranslations = words.get(index).getTranslations();
                options.add(i, optionTranslations.get(random.nextInt(optionTranslations.size())).getWord());
            }

            Collections.shuffle(options);

            int correctOptionId = options.indexOf(answer);

            try {
                absSender.execute(SendPoll.builder()
                        .chatId(chat.getId().toString())
                        .type("quiz")
                        .question(question)
                        .options(options)
                        .correctOptionId(correctOptionId)
                        .build());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }


    }
}
