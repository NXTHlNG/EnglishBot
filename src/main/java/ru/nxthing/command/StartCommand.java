package ru.nxthing.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class StartCommand extends DefaultBotCommand {
    private static final String commandIdentifier = "start";
    private static final String description = "Стартовая команда";

    private static final String message = "Стартовое сообщение";

    private static final ReplyKeyboardMarkup keyboard = ReplyKeyboardMarkup
            .builder()
            .keyboardRow(new KeyboardRow(List.of(KeyboardButton.builder().text("/help").build())))
            .build();

    public StartCommand() {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
        try {
            absSender.execute(SendMessage
                    .builder()
                    .chatId(chat.getId().toString())
                    .text(message)
                    .replyMarkup(keyboard)
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
