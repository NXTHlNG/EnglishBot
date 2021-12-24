package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.factory.KeyboardFactory;
import ru.nxthing.repository.entities.WordCollection;
import ru.nxthing.service.WordCollectionService;

import java.util.List;

@Component
public class ShowCollectionCallback extends AbstractBotCallback {
    public static final String callbackName = "collection_show";

    private final WordCollectionService wordCollectionService;

    public ShowCollectionCallback(WordCollectionService wordCollectionService) {
        super(callbackName);
        this.wordCollectionService = wordCollectionService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, CallbackQuery callbackQuery, String[] arguments) {
        int collectionIndex = Integer.parseInt(arguments[0]);

        List<WordCollection> collections = wordCollectionService.findAll();
        StringBuilder message = new StringBuilder();

        if (collectionIndex < 0 || collectionIndex >= collections.size()) return;

        WordCollection collection = collections.get(collectionIndex);
        message.append("<b>Коллекция ")
                .append(collectionIndex + 1)
                .append("/")
                .append(collections.size())
                .append("</b>\n")
                .append(collection.toString());

        try {
            absSender.execute(EditMessageText.builder()
                    .chatId(chat.getId().toString())
                    .messageId(messageId)
                    .text(message.toString())
                    .parseMode("HTML")
                    .replyMarkup(KeyboardFactory.createMultipleKeyboard(collectionIndex))
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
