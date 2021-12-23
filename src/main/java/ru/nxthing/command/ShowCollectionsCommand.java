package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.repository.entities.Word;
import ru.nxthing.repository.entities.WordCollection;
import ru.nxthing.service.WordCollectionService;

import java.util.List;
import java.util.Set;

@Component
public class ShowCollectionsCommand extends AbstarctBotCommand {
    private static final String commandIdentifier = "show_collections";
    private static final String description = "show_collections";

    private WordCollectionService wordCollectionService;

    public ShowCollectionsCommand(WordCollectionService wordCollectionService) {
        super(commandIdentifier, description);
        this.wordCollectionService = wordCollectionService;
    }


    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
        System.out.println("show_collections");

        List<WordCollection> collections = wordCollectionService.findAll();
        StringBuilder message = new StringBuilder();

        for (WordCollection collection : collections) {
            message.append("id коллекции: ").append(collection.getId()).append("\n").append("Популярность: ").append(collection.getPopularity()).append("\n");
            Set<Word> words = collection.getWords();
            for (Word word : words) {
                message.append(word.getWord()).append("; ");
            }
        }

        try {
            absSender.execute(SendMessage.builder().chatId(chat.getId().toString()).text(message.toString()).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
