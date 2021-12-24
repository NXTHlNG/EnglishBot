package ru.nxthing.command;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Configuration
public class KeyboardsConfig {
    @Bean
    public ReplyKeyboardMarkup startKeyboard(CommandRegistry commandRegistry) {
        Collection<BotCommand> commands = commandRegistry.getRegisteredCommands()
                .stream()
                .sorted(Comparator.comparing(BotCommand::getCommandIdentifier))
                .collect(Collectors.toList());
        KeyboardRow keyboardRow = new KeyboardRow();
        for (BotCommand command : commands) {
            keyboardRow.add("/" + command.getCommandIdentifier());
        }

        return ReplyKeyboardMarkup.builder()
                .keyboardRow(keyboardRow)
                .resizeKeyboard(true)
                .build();
    }
}
