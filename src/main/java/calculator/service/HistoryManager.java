package calculator.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private static final String HISTORY_FILE = "calculator_history.txt";

    public static void saveCalculation(String expression, String result) {
        try {
            String entry = expression + " = " + result + System.lineSeparator();
            Files.write(Paths.get(HISTORY_FILE), entry.getBytes(), 
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getHistory() {
        try {
            Path path = Paths.get(HISTORY_FILE);
            if (Files.exists(path)) {
                return Files.readAllLines(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void clearHistory() {
        try {
            Files.deleteIfExists(Paths.get(HISTORY_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
