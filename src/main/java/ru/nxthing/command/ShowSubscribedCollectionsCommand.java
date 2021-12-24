package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.factory.KeyboardFactory;
import ru.nxthing.repository.entities.WordCollection;
import ru.nxthing.service.BotUserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShowSubscribedCollectionsCommand extends AbstarctBotCommand {
    private static final String commandIdentifier = "show_subscribes";
    private static final String description = "Показывает все подписки на коллеции";

    private BotUserService botUserService;

    public ShowSubscribedCollectionsCommand(BotUserService botUserService) {
        super(commandIdentifier, description);
        this.botUserService = botUserService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
//        Set<WordCollection> subscribes = botUserRepository.findById(chat.getId()).get().getSubscribedCollections();
        List<WordCollection> subscribes = new ArrayList<>(botUserService.findByChatId(chat.getId()).get().getSubscribedCollections());

        StringBuilder message = new StringBuilder();

        SendMessage sendMessage = new SendMessage();

        if (subscribes.isEmpty()) {
            message.append("У вас нет подписок на коллекции");
        } else {
            WordCollection subscribe = subscribes.get(0);
            message.append("<b>Коллекция 1/")
                    .append(subscribes.size())
                    .append("</b>\n")
                    .append(subscribe.toString());
            sendMessage.setReplyMarkup(KeyboardFactory.createSubscribesKeyboard(0));
        }

        try {
            sendMessage.setText(message.toString());
            sendMessage.setParseMode("HTML");
            sendMessage.setChatId(chat.getId().toString());
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
