package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.repository.entities.BotUser;
import ru.nxthing.service.BotUserService;

@Component
public class EndCommand extends AbstarctBotCommand {
    private static final String commandIdentifier = "end";
    private static final String description = "Закончить работу с ботом";
    private static final String message = "Работа с ботом завершена";


    private final BotUserService botUserService;

    public EndCommand(BotUserService botUserService) {
        super(commandIdentifier, description);
        this.botUserService = botUserService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
        long chatId = chat.getId();
        botUserService.findByChatId(chatId).ifPresentOrElse(
                botUser -> {
                    botUser.setActive(false);
                    botUserService.save(botUser);
                },
                () -> {}
        );
        try {
            absSender.execute(SendMessage
                    .builder()
                    .chatId(Long.toString(chatId))
                    .text(message)
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
