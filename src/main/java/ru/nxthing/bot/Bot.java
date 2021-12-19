package ru.nxthing.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.command.StartCommand;

@Component
public class Bot extends TelegramLongPollingCommandBot {
    @Value("${telegrambot.username}")
    private String username;

    @Value("${telegrambot.token}")
    private String token;

    {
        register(new HelpCommand("help", "Показывает все команды. Используйте /help [command] для большей информации", "Показывает все команды.\n /help [command] покажет детальную информацию"));
        register(new StartCommand());
    }

    @Override
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

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}