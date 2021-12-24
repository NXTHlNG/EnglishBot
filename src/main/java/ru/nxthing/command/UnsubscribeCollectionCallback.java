package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.repository.entities.BotUser;
import ru.nxthing.repository.entities.WordCollection;
import ru.nxthing.service.BotUserService;
import ru.nxthing.service.WordCollectionService;

@Component
public class UnsubscribeCollectionCallback extends AbstractBotCallback {
    public static final String callbackName = "collection_unsubscribe";

    private final WordCollectionService wordCollectionService;
    private final BotUserService botUserService;

    public UnsubscribeCollectionCallback(WordCollectionService wordCollectionService, BotUserService botUserService) {
        super(callbackName);
        this.wordCollectionService = wordCollectionService;
        this.botUserService = botUserService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, CallbackQuery callbackQuery, String[] arguments) {
        int collectionIndex = Integer.parseInt(arguments[0]);

        BotUser botUser = botUserService.findByChatId(chat.getId()).get();
        WordCollection collection = wordCollectionService.findAll().get(collectionIndex);

        botUser.getSubscribedCollections().remove(collection);
        botUserService.save(botUser);

        collection.getBotUserList().remove(botUser);
        wordCollectionService.save(collection);

        try {
            absSender.execute(SendMessage
                    .builder()
                    .chatId(chat.getId().toString())
                    .text("Подписка отменена")
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
