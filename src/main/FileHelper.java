package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    public static List<String> readFileToLines(String filePath) {
        BufferedReader reader = FileHelper.getActualPath(filePath);
        List<String> lines = new ArrayList<>();
        try {
            String s;
            while ((s = reader.readLine()) != null)
                lines.add(s);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void writeStringToFile(String filePath, String data) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                String[] temp = filePath.split("/");
                String dir = filePath.replace(temp[temp.length - 1], "");
                File mkdir = new File(dir);
                mkdir.mkdirs();
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(data);
            bufferWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedReader getActualPath(String path) {
        try {
            return new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
