package ru.nxthing.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface BotCommand {

    String getCommandIdentifier();

    String getCommandDescription();

    void processMessage(AbsSender absSender, Message message, String[] arguments);
}
