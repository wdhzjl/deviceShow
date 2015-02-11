package com.bosstun.util;

import java.io.IOException;

public class BootManager {
    public static void reboot(){
        String cmd = "su -c reboot";
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
