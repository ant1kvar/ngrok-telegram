package com.kost.config;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TelegramConfig {
    private  String urlSetWebHok;
    private  String urlDeleteWebHok;
    private String urlToWebHook;
    private static Logger logger = Logger.getLogger(TelegramConfig.class.getName());

    public TelegramConfig() {
    }

    public TelegramConfig(String token_telegram, String url_to_webhook) {
        this.urlSetWebHok = "https://api.telegram.org/bot" + token_telegram+"/setWebhook?url=####" + url_to_webhook;
        this.urlDeleteWebHok = "https://api.telegram.org/bot" + token_telegram+"/deleteWebhook";
        this.urlToWebHook = url_to_webhook;
    }

    public void DELETE_WEB_HOOK() throws IOException {
        logger.info("Отхуячиваем старй вебхук...");
        String url = this.urlDeleteWebHok;

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        if (response.toString().contains("Webhook was deleted")) {
            logger.info("Отхуячили!\n");
        }
    }

    public void SET_WEB_HOOT(String host) throws IOException {
        logger.info("Прихуяриваем новый вебхук...");

        String url = this.urlSetWebHok.replace("####", host);

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        if (response.toString().contains("Webhook was set")) {
            logger.info("Вебхук прихуячен!");
        } else {
            logger.warn("Все плохо, проверь параметры и перезапусти.\n Сервер телеги вернул >> "+ response.toString());
        }
    }

    public String getUrlSetWebHok() {
        return urlSetWebHok;
    }

    public void setUrlSetWebHok(String urlSetWebHok) {
        this.urlSetWebHok = urlSetWebHok;
    }

    public String getUrlDeleteWebHok() {
        return urlDeleteWebHok;
    }

    public void setUrlDeleteWebHok(String urlDeleteWebHok) {
        this.urlDeleteWebHok = urlDeleteWebHok;
    }

    public String getUrlToWebHook() {
        return urlToWebHook;
    }

    public void setUrlToWebHook(String urlToWebHook) {
        this.urlToWebHook = urlToWebHook;
    }
}
