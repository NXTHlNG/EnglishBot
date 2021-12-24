package ru.nxthing.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nxthing.factory.KeyboardFactory;
import ru.nxthing.repository.entities.WordCollection;
import ru.nxthing.service.WordCollectionService;

import java.util.List;

@Component
public class ShowCollectionsCommand extends AbstarctBotCommand {
    private static final String commandIdentifier = "show_collections";
    private static final String description = "show_collections";

    private static final String NO_INDEX_MESSAGE = "Коллекции с таким номером нет";
    private static final String WRONG_INDEX_FORMAT_MESSAGE = "Неверный формат ввода индекса";

    private final WordCollectionService wordCollectionService;

    public ShowCollectionsCommand(WordCollectionService wordCollectionService) {
        super(commandIdentifier, description);
        this.wordCollectionService = wordCollectionService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
        if (arguments.length > 0) {
            try {
                int collectionIndex = Integer.parseInt(arguments[0]) - 1;
                execute(absSender, chat, collectionIndex, KeyboardFactory.createSingleKeyboard(collectionIndex));
            } catch (NumberFormatException numberFormatException) {
                try {
                    absSender.execute(SendMessage.builder()
                            .chatId(chat.getId().toString())
                            .text(WRONG_INDEX_FORMAT_MESSAGE)
                            .parseMode("HTML")
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else {
            execute(absSender, chat, 0, KeyboardFactory.createMultipleKeyboard(0));
        }
    }

    public void execute(AbsSender absSender, Chat chat, int collectionIndex, InlineKeyboardMarkup keyboardMarkup) {
        List<WordCollection> collections = wordCollectionService.findAll();
        StringBuilder message = new StringBuilder();

        try {
            WordCollection collection = collections.get(collectionIndex);
            message.append("<b>Коллекция ")
                    .append(collectionIndex + 1)
                    .append("/")
                    .append(collections.size())
                    .append("</b>\n")
                    .append(collection.toString());
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            message.append(NO_INDEX_MESSAGE);
        }

        try {
            absSender.execute(SendMessage.builder()
                    .chatId(chat.getId().toString())
                    .text(message.toString())
                    .parseMode("HTML")
                    .replyMarkup(keyboardMarkup)
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
