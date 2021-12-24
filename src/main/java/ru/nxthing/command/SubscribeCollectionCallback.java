package ru.nxthing.command;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class SubscribeCollectionCallback extends AbstractBotCallback {
    public static final String callbackName = "collection_subscribe";

    public SubscribeCollectionCallback() {
        super(callbackName);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, CallbackQuery callbackQuery, String[] arguments) {

    }
}
