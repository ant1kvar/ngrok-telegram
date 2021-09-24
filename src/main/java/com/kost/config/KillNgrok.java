package com.kost.config;

import java.io.IOException;

public class KillNgrok {
    public static void init() {
        try {
            String property = System.getProperty("os.name");
            Process process = null;
            if (property.contains("Windows")) {
                process = Runtime.getRuntime().exec("tskill /A ngrok");
            } else {
                process = Runtime.getRuntime().exec("killall ngrok");
            }
            process.waitFor();
            process.destroy();
        } catch (IOException e) {

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
