package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.factory.KeyboardFactory;
import ru.nxthing.repository.entities.WordCollection;
import ru.nxthing.service.BotUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ShowSubscribedCollectionsCallback extends AbstractBotCallback {
    public static final String callbackName = "subscribe_show";

    private final BotUserService botUserService;

    public ShowSubscribedCollectionsCallback(BotUserService botUserService) {
        super(callbackName);
        this.botUserService = botUserService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, CallbackQuery callbackQuery, String[] arguments) {
        int collectionIndex = Integer.parseInt(arguments[0]);

        List<WordCollection> subscribes = new ArrayList<>(botUserService.findByChatId(chat.getId()).get().getSubscribedCollections());
        StringBuilder message = new StringBuilder();

        if (collectionIndex < 0 || collectionIndex >= subscribes.size()) return;

        WordCollection subscribe = subscribes.get(collectionIndex);
        message.append("<b>Коллекция ")
                .append(collectionIndex + 1)
                .append("/")
                .append(subscribes.size())
                .append("</b>\n")
                .append(subscribe.toString());

        try {
            absSender.execute(EditMessageText.builder()
                    .chatId(chat.getId().toString())
                    .messageId(messageId)
                    .text(message.toString())
                    .parseMode("HTML")
                    .replyMarkup(KeyboardFactory.createSubscribesKeyboard(collectionIndex))
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
