package ru.nxthing.command;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface BotCallback {
    String getCallbackName();

    void execute(AbsSender absSender, Message message, CallbackQuery callbackQuery, String[] arguments);
}
