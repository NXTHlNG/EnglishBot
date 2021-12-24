package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.bot.Bot;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class HelpCommand extends AbstarctBotCommand {

    private static final String COMMAND_IDENTIFIER = "help";
    private static final String COMMAND_DESCRIPTION = "Показывает все команды. Используй /help [команда] для большей информации";


    public static String getHelpText(Collection<BotCommand> botCommands) {
        StringBuilder reply = new StringBuilder();
        Collection<BotCommand> sorted = botCommands.stream().sorted(Comparator.comparing(BotCommand::getCommandIdentifier)).collect(Collectors.toList());
        for (BotCommand com : sorted) {
            reply.append(com.toString()).append(System.lineSeparator()).append(System.lineSeparator());
        }
        return reply.toString();
    }

    public HelpCommand() {
        super(COMMAND_IDENTIFIER, COMMAND_DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
        if (absSender instanceof Bot) {
            Bot bot = (Bot) absSender;

            if (arguments.length > 0) {
                BotCommand command = bot.getRegisteredCommand(arguments[0]);
                String reply;
                if (command == null) {
                    // TODO: 24.12.2021 add exception 
                    reply = "Такой команды не существует";
                }
                else {
                    reply = command.getCommandDescription();
                }
                try {
                    absSender.execute(SendMessage.builder().chatId(chat.getId().toString()).text(reply).parseMode("HTML").build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                String reply = getHelpText(bot.getRegisteredCommands());
                try {
                    absSender.execute(SendMessage.builder().chatId(chat.getId().toString()).text(reply).parseMode("HTML").build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
