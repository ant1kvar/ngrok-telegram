package com.kost.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
@NoArgsConstructor
public class PropertiesLoad {
    private String TOKEN_TELEGRAM;
    private String URL_TO_WEBHOOK;
    private String PORT;
    private String NGROK_TOKEN;
    private String PORT_RUN_APP;

    private static Logger logger = Logger.getLogger(PropertiesLoad.class);

    public static PropertiesLoad build() throws InterruptedException {
        try (InputStream input = new FileInputStream("config.properties")) {
            PropertiesLoad load = new PropertiesLoad();
            Properties prop = new Properties();
            prop.load(input);
            load.setTOKEN_TELEGRAM(prop.getProperty("TOKEN_TELEGRAM"));
            load.setURL_TO_WEBHOOK(prop.getProperty("URL_TO_WEBHOOK"));
            load.setPORT(prop.getProperty("PORT"));
            load.setNGROK_TOKEN(prop.getProperty("NGROK_TOKEN"));
            load.setPORT_RUN_APP(prop.getProperty("PORT_RUN_APP"));
            return load;
        } catch (IOException ex) {
            logger.warn("Всё пошло по пизде...проверяй конфиг");
            Thread.sleep(5000);
            System.exit(1);
        }
        return null;
    }

    public String getTOKEN_TELEGRAM() {
        return TOKEN_TELEGRAM;
    }

    public void setTOKEN_TELEGRAM(String TOKEN_TELEGRAM) {
        this.TOKEN_TELEGRAM = TOKEN_TELEGRAM;
    }

    public String getURL_TO_WEBHOOK() {
        return URL_TO_WEBHOOK;
    }

    public void setURL_TO_WEBHOOK(String URL_TO_WEBHOOK) {
        this.URL_TO_WEBHOOK = URL_TO_WEBHOOK;
    }

    public String getPORT() {
        return PORT;
    }

    public void setPORT(String PORT) {
        this.PORT = PORT;
    }

    public String getNGROK_TOKEN() {
        return NGROK_TOKEN;
    }

    public void setNGROK_TOKEN(String NGROK_TOKEN) {
        this.NGROK_TOKEN = NGROK_TOKEN;
    }

    public String getPORT_RUN_APP() {
        return PORT_RUN_APP;
    }

    public void setPORT_RUN_APP(String PORT_RUN_APP) {
        this.PORT_RUN_APP = PORT_RUN_APP;
    }
}


