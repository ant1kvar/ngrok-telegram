package com.kost;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.conf.JavaNgrokConfig;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Proto;
import com.github.alexdlaird.ngrok.protocol.Tunnel;
import com.kost.config.KillNgrok;
import com.kost.config.Log;
import com.kost.config.PropertiesLoad;
import com.kost.config.TelegramConfig;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static Logger LOGGER = null;
    static {
        InputStream stream = Main.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            LOGGER= Logger.getLogger(Main.class.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        KillNgrok.init();
        PropertiesLoad load = PropertiesLoad.build();
        try {

            TelegramConfig telegramConfig = new TelegramConfig(load.getTOKEN_TELEGRAM(), load.getURL_TO_WEBHOOK());
            final int port = Integer.parseInt(System.getenv().getOrDefault("PORT", load.getPORT_RUN_APP()));
            final HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            final JavaNgrokConfig config = new JavaNgrokConfig.Builder()
                    .withAuthToken(load.getNGROK_TOKEN())
                    .build();
            final NgrokClient ngrokClient = new NgrokClient.Builder()
                    .withJavaNgrokConfig(config)
                    .build();
            final CreateTunnel createSSLTunnel = new CreateTunnel.Builder()
                    .withProto(Proto.HTTP)
                    .withAddr(load.getPORT())
                    .build();

            final Tunnel tunnel = ngrokClient.connect(createSSLTunnel);
            System.out.println("Your host HTTP:  " + tunnel.getPublicUrl());
            String url = tunnel.getPublicUrl().replace("http:", "https:");
            System.out.println("Your host HTTPS:  " + url);
            telegramConfig.DELETE_WEB_HOOK();
            telegramConfig.SET_WEB_HOOT(url);
            System.out.println("Твой урл в куда будет приходить запрос : localhost:" + load.getPORT() + load.getURL_TO_WEBHOOK());
            server.start();
        } catch (BindException e) {
            Log.logWarn("Порт "+load.getPORT_RUN_APP() +" к сожалению занят какой-то ебатой. Поменяй в конфиге");
        }
    }
}
