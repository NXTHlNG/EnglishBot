package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.factory.KeyboardFactory;
import ru.nxthing.service.WordCollectionService;

@Component
public class ShowWordsCallback extends AbstractBotCallback {
    public static final String callbackName = "collection_show_words";

    private final WordCollectionService wordCollectionService;

    public ShowWordsCallback(WordCollectionService wordCollectionService) {
        super(callbackName);
        this.wordCollectionService = wordCollectionService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, CallbackQuery callbackQuery, String[] arguments) {
        if (arguments.length > 0) {
            int collectionIndex = Integer.parseInt(arguments[0]);
            String message = wordCollectionService.findAll().get(collectionIndex).toStringWithWords();
            EditMessageText newMessage = EditMessageText.builder()
                    .chatId(chat.getId().toString())
                    .messageId(messageId)
                    .text(message)
                    .parseMode("HTML")
                    .replyMarkup(KeyboardFactory.createShowWordsKeyboard(collectionIndex))
                    .build();
            try {
                absSender.execute(newMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
