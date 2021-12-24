package ru.nxthing.command;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class AbstractBotCallback implements BotCallback {
    private final String callbackName;

    public AbstractBotCallback(String callbackName) {
        this.callbackName = callbackName;
    }

    @Override
    public String getCallbackName() {
        return callbackName;
    }

    @Override
    public final void execute(AbsSender absSender, Message message, CallbackQuery callbackQuery, String[] arguments) {
        execute(absSender, message.getFrom(), message.getChat(), message.getMessageId(), callbackQuery, arguments);
        answerCallback(absSender, callbackQuery);
    }

    public final void answerCallback(AbsSender absSender, CallbackQuery callbackQuery) {
        try {
            absSender.execute(AnswerCallbackQuery.builder().callbackQueryId(callbackQuery.getId()).showAlert(false).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public abstract void execute(AbsSender absSender, User user, Chat chat, Integer messageId, CallbackQuery callbackQuery, String[] arguments);

}
