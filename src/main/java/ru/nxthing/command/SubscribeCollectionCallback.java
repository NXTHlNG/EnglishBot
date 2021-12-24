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
public class SubscribeCollectionCallback extends AbstractBotCallback {
    public static final String callbackName = "collection_subscribe";
    public static final String SUBSCRIBE_SUCCESS = "Вы подписались на коллекцию";
    public static final String SUBSCRIBE_NOT_SUCCESS = "Вы уже подписаны на эту коллекцию";

    private final WordCollectionService wordCollectionService;
    private final BotUserService botUserService;

    public SubscribeCollectionCallback(WordCollectionService wordCollectionService, BotUserService botUserService) {
        super(callbackName);
        this.wordCollectionService = wordCollectionService;
        this.botUserService = botUserService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, CallbackQuery callbackQuery, String[] arguments) {
        int collectionIndex = Integer.parseInt(arguments[0]);

        WordCollection collection = wordCollectionService.findAll().get(collectionIndex);

        BotUser botUser = botUserService.findByChatId(chat.getId()).get();

        String message;

        if (botUser.getSubscribedCollections().stream().anyMatch(wordCollection -> wordCollection.getId() == collection.getId())) {
            message = SUBSCRIBE_NOT_SUCCESS;
        }
        else {
            botUser.getSubscribedCollections().add(collection);
            botUserService.save(botUser);
            message = SUBSCRIBE_SUCCESS;
        }

        try {
            absSender.execute(SendMessage
                    .builder()
                    .chatId(chat.getId().toString())
                    .text(message)
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
