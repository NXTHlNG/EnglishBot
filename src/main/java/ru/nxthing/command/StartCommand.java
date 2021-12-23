package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.repository.entities.BotUser;
import ru.nxthing.service.BotUserService;

import java.util.List;

@Component
public class StartCommand extends AbstarctBotCommand {
    private static final String commandIdentifier = "start";
    private static final String description = "Стартовая команда";

    private static final String message = "Стартовое сообщение";

    private final BotUserService botUserService;

    private static final ReplyKeyboardMarkup keyboard = ReplyKeyboardMarkup
            .builder()
            .keyboardRow(new KeyboardRow(List.of(KeyboardButton.builder().text("/help").build())))
            .build();

    public StartCommand(BotUserService botUserService) {
        super(commandIdentifier, description);
        this.botUserService = botUserService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
        long chatId = chat.getId();
        botUserService.findByChatId(chatId).ifPresentOrElse(
                botUser -> {
                    botUser.setActive(true);
                    botUserService.save(botUser);
                },
                () -> {
                    BotUser botUser = new BotUser();
                    botUser.setActive(true);
                    botUser.setChatId(chatId);
                    botUserService.save(botUser);
                }
        );
        try {
            absSender.execute(SendMessage
                    .builder()
                    .chatId(Long.toString(chatId))
                    .text(message)
                    .replyMarkup(keyboard)
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
