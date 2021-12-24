package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class InvalidCommand implements BotCommand {
    private static final String textMessage = "Неверная команда, используй /help, чтобы увидеть список доступных команд";

    @Override
    public String getCommandIdentifier() {
        return null;
    }

    @Override
    public String getCommandDescription() {
        return null;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        try {
            absSender.execute(SendMessage.builder().chatId(message.getChatId().toString()).text(textMessage).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
