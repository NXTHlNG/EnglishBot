package ru.nxthing.factory;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.nxthing.command.SubscribeCollectionCallback;
import ru.nxthing.command.UnsubscribeCollectionCallback;

import java.util.List;

public class KeyboardFactory {
    public static InlineKeyboardMarkup createSingleKeyboard(int index) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(createShowWordsButton(index)))
                .build();
    }

    public static InlineKeyboardMarkup createMultipleKeyboard(int index) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(createShowWordsButton(index), createSubscribeCollectionButton(index)))
                .keyboardRow(List.of(createPreviousCollectionButton(index), createNextCollectionButton(index)))
                .build();
    }

    private static InlineKeyboardButton createShowWordsButton(int index) {
        return InlineKeyboardButton.builder()
                .text("Показать слова")
                .callbackData("collection_show_words" + " " + index)
                .build();
    }

    private static InlineKeyboardButton createSubscribeCollectionButton(int index) {
        return InlineKeyboardButton.builder()
                .text("Подписаться")
                .callbackData(SubscribeCollectionCallback.callbackName + " " + index)
                .build();
    }

    private static InlineKeyboardButton createNextCollectionButton(int index) {
        return InlineKeyboardButton.builder()
                .text("Далее")
                .callbackData("collection_show" + " " + (index + 1))
                .build();
    }

    private static InlineKeyboardButton createPreviousCollectionButton(int index) {
        return InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData("collection_show" + " " + (index - 1))
                .build();
    }

    public static InlineKeyboardMarkup createShowWordsKeyboard(int index) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(createWordsBackButton(index)))
                .build();
    }

    private static InlineKeyboardButton createWordsBackButton(int index) {
        return InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData("collection_show" + " " + index)
                .build();
    }

    public static InlineKeyboardMarkup createSubscribesKeyboard(int index) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(createUnsubscribeCollectionButton(index)))
                .keyboardRow(List.of(createPreviousSubscribeButton(index), createNextSubscribeButton(index)))
                .build();
    }

    private static InlineKeyboardButton createNextSubscribeButton(int index) {
        return InlineKeyboardButton.builder()
                .text("Далее")
                .callbackData("subscribe_show" + " " + (index + 1))
                .build();
    }

    private static InlineKeyboardButton createPreviousSubscribeButton(int index) {
        return InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData("subscribe_show" + " " + (index - 1))
                .build();
    }

    private static InlineKeyboardButton createUnsubscribeCollectionButton(int index) {
        return InlineKeyboardButton.builder()
                .text("Отписаться")
                .callbackData("collection_unsubscribe" + " " + index)
                .build();
    }
}
