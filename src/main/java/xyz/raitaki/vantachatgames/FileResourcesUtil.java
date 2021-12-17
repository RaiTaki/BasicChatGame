package xyz.raitaki.vantachatgames;

import de.leonhard.storage.Json;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileResourcesUtil {

    public InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
    public void saveFile(String name,InputStream is) {
        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            new Json(name, main.getInstance().getDataFolder().toString(),is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}