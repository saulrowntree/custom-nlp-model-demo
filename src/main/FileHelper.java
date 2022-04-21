package main;
/**
 * File handle class
 *
 * @author naplues
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    public static List<String> readFileToLines(String filePath, boolean... args) {
        BufferedReader reader = null;
        if (args.length > 0 && args[0])
            reader = FileHelper.getExternalPath(filePath);
        else
            reader = FileHelper.getActualPath(filePath);
        List<String> lines = new ArrayList<>();
        try {
            String s = null;
            while ((s = reader.readLine()) != null)
                lines.add(s);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void writeStringToFile(String filePath, String data, boolean... a) {
        try {
            // true = append file
            File file = new File(filePath);
            // 文件不存在
            if (!file.exists()) {
                String[] temp = filePath.split("/");
                String dir = filePath.replace(temp[temp.length - 1], "");
                File mkdir = new File(dir);
                mkdir.mkdirs();
                file.createNewFile();
            }
            boolean append = false;
            if (a.length == 1) {
                append = a[0];
            }
            FileWriter fileWritter = new FileWriter(file, append);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.close();
            fileWritter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedReader getActualPath(String path) {
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedReader getExternalPath(String path) {
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
