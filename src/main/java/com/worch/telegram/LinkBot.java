package com.worch.telegram;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class LinkBot extends TelegramLongPollingBot {

    private static final String SITE_URL = "https://worch.com";

    @Value("${telegram.bot.username:stub-bot}")
    private String botUsername;

    @Value("${telegram.bot.token:stub-token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallback(update);
        }
    }

    private void handleTextMessage(Update update) {
        String text = update.getMessage().getText().trim();
        Long chatId = update.getMessage().getChatId();

        if ("/start".equals(text)) {
            sendMessage(chatId, "Добро пожаловать! Выберите действие:");
        }
    }

    private void handleCallback(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String callbackId = update.getCallbackQuery().getId();

        try {
            AnswerCallbackQuery answer = new AnswerCallbackQuery();
            answer.setCallbackQueryId(callbackId);
            execute(answer);
        } catch (TelegramApiException e) {
            log.warn("Failed to answer callback: {}", e.getMessage());
        }

        String responseText = switch (callbackData) {
            case "OPEN_SITE" -> "Вот ссылка на сайт: " + SITE_URL;
            case "HELP" -> "Это демо-бот. Нажмите 'Перейти на сайт', чтобы открыть его.";
            default -> "Неизвестная команда.";
        };

        sendMessage(chatId, responseText);
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId.toString());
        msg.setText(text);
        msg.setReplyMarkup(buildMainMenu());

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.warn("Error sending message to chat {}: {}", chatId, e.getMessage(), e);
        }
    }

    private InlineKeyboardMarkup buildMainMenu() {
        InlineKeyboardButton siteButton = new InlineKeyboardButton();
        siteButton.setText("Перейти на сайт 🌐");
        siteButton.setCallbackData("OPEN_SITE");

        InlineKeyboardButton helpButton = new InlineKeyboardButton();
        helpButton.setText("Помощь ❓");
        helpButton.setCallbackData("HELP");

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(siteButton));
        keyboard.add(List.of(helpButton));

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        return markup;
    }
}
