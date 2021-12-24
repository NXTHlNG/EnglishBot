package ru.nxthing.factory;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class KeyboardFactory {
    public static InlineKeyboardMarkup createSingleKeyboard(int index) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(createShowWordsButton(index)))
                .build();
    }

    public static InlineKeyboardMarkup createMultipleKeyboard(int index) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(createShowWordsButton(index)))
                .keyboardRow(List.of(createPreviousCollectionButton(index), createNextCollectionButton(index)))
                .build();
    }

    private static InlineKeyboardButton createShowWordsButton(int index) {
        return InlineKeyboardButton.builder()
                .text("Показать слова")
                .callbackData("collection_show_words" + " " + index)
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
}
