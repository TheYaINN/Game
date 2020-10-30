package de.joachimsohn.engine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {


    public static String loadAsString(String path) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Class.class.getResourceAsStream(path)))) {
            br.lines().forEach(l -> sb.append(l).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
