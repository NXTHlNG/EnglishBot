package ru.nxthing.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandRegistry {

    private final Map<String, BotCommand> commandRegistryMap;

    private final InvalidCommand invaildCommand;

    public CommandRegistry(@Qualifier("commandRegistryMap") Map<String, BotCommand> commandRegistryMap,InvalidCommand invalidCommand) {
        this.commandRegistryMap = commandRegistryMap;
        this.invaildCommand = invalidCommand;
    }

    public final boolean executeCommand(AbsSender absSender, Message message) {
        if (message.hasText()) {
            String text = message.getText().trim();
            if (text.startsWith(AbstarctBotCommand.COMMAND_INIT_CHARACTER)) {
                String commandMessage = text.substring(1);
                String[] commandSplit = commandMessage.split(AbstarctBotCommand.COMMAND_PARAMETER_SEPARATOR_REGEXP);
                String command = commandSplit[0].toLowerCase();

                if (commandRegistryMap.containsKey(command)) {
                    String[] parameters = Arrays.copyOfRange(commandSplit, 1, commandSplit.length);
                    commandRegistryMap.get(command).processMessage(absSender, message, parameters);
                    return true;
                }
            }
        }
        return false;
    }

    public final void executeInvalidCommand(AbsSender absSender, Message message) {
        invaildCommand.processMessage(absSender, message, new String[]{});
    }
}
