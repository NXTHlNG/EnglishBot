package ru.nxthing.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.command.CommandRegistry;

@Component
public class Bot extends TelegramLongPollingBot {
    private final CommandRegistry commandRegistry;

    @Value("${telegrambot.username}")
    private String username;

    @Value("${telegrambot.token}")
    private String token;

    public Bot(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (!filter(message)) {
                if (!commandRegistry.executeCommand(this, message)) {
                    processInvalidCommandUpdate(update);
                }
                return;
            }
        }
        processNonCommandUpdate(update);
    }

    protected void processInvalidCommandUpdate(Update update) {
        commandRegistry.executeInvalidCommand(this, update.getMessage());
    }

    public void processNonCommandUpdate(Update update) {
        try {
            execute(SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString())
                    .text(update.getMessage().getText() + "— не является командой, используйте /help для вывода доступных команд")
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected boolean filter(Message message) {
        return false;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}