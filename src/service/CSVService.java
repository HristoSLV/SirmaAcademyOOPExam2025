package service;

import java.io.*;
import java.util.*;

public class CSVService {

    public static List<String> readLines(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    public static void writeLines(String filename, List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (String line : lines) {
                pw.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ensureFileExists(String filepath) {
        try {
            File file = new File(filepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Created new file: " + filepath);
                }
            }
        } catch (IOException e) {
            System.err.println("Error creating file " + filepath + ": " + e.getMessage());
        }
    }

}