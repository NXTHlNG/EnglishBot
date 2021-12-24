package ru.nxthing.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.*;

@Component
public class CommandRegistry {

    private final Map<String, BotCommand> commandRegistryMap;

    private final Map<String, BotCallback> callbackRegistryMap;

    private final InvalidCommand invalidCommand;

    public CommandRegistry(@Qualifier("commandRegistryMap") Map<String, BotCommand> commandRegistryMap,
                           @Qualifier("callbackRegistryMap") Map<String, BotCallback> callbackRegistryMap,
                           InvalidCommand invalidCommand) {
        this.commandRegistryMap = commandRegistryMap;
        this.callbackRegistryMap = callbackRegistryMap;
        this.invalidCommand = invalidCommand;
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

    public final void executeCallback(AbsSender absSender, CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        String[] callbackDataSplit = callbackData.split("\\s+");
        String callbackName = callbackDataSplit[0];

        if (callbackRegistryMap.containsKey(callbackName)) {
            String[] arguments = Arrays.copyOfRange(callbackDataSplit, 1, callbackDataSplit.length);
            callbackRegistryMap.get(callbackName).execute(absSender, callbackQuery.getMessage(), callbackQuery, arguments);
        }
    }

    public final void executeInvalidCommand(AbsSender absSender, Message message) {
        invalidCommand.processMessage(absSender, message, new String[]{});
    }

    public Collection<BotCommand> getRegisteredCommands() {
        return commandRegistryMap.values();
    }

    public BotCommand getRegisteredCommand(String commandIdentifier) {
        return commandRegistryMap.get(commandIdentifier);
    }
}
