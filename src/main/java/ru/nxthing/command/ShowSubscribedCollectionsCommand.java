package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.repository.BotUserRepository;
import ru.nxthing.repository.entities.BotUser;
import ru.nxthing.repository.entities.WordCollection;

import java.util.Set;

@Component
public class ShowSubscribedCollectionsCommand extends AbstarctBotCommand {
    private static final String commandIdentifier = "show_subscribes";
    private static final String description = "Показывает все подписки на коллеции";

    private BotUserRepository botUserRepository;

    public ShowSubscribedCollectionsCommand(BotUserRepository botUserRepository) {
        super(commandIdentifier, description);
        this.botUserRepository = botUserRepository;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
        Set<WordCollection> subscribes = botUserRepository.findById(chat.getId()).get().getSubscribedCollections();

        StringBuilder message = new StringBuilder();

        for (WordCollection collection : subscribes) {
            message.append(collection.toString()).append("\n\n");
        }

        if (message.toString().isEmpty()) {
            message.append("У вас нет подписок на коллекции");
        }

        try {
            absSender.execute(SendMessage.builder().chatId(chat.getId().toString()).text(message.toString()).parseMode("HTML").build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
