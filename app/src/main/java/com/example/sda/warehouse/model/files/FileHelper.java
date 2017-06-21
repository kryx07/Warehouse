package com.example.sda.warehouse.model.files;

import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by sda on 21.06.17.
 */

public class FileHelper {

    public static String getContent(BufferedReader bufferedReader) throws IOException {

        String text = "";
        String temp;

        do {
            temp = bufferedReader.readLine();

            if (temp != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    text += temp + System.lineSeparator();
                } else {
                    text += temp + '\n';
                }
            }
        } while (temp != null);

        return text;
    }
}
