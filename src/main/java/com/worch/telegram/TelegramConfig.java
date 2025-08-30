package com.worch.telegram;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class TelegramConfig {

  private final LinkBot linkBot;

  @PostConstruct
  public void registerBot() {

    try {
      linkBot.clearWebhook();
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      botsApi.registerBot(linkBot);
      System.out.println("LinkBot registered");
      log.info("LinkBot registered");

    } catch (TelegramApiException e) {
      log.warn(e.getMessage());
    }
  }
}
