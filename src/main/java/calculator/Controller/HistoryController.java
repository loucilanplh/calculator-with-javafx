package calculator.Controller;

import calculator.CalculatorApp;
import calculator.service.HistoryManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.util.List;

public class HistoryController {

    @FXML
    private ListView<String> historyListView;

    @FXML
    public void initialize() {
        loadHistory();
    }

    private void loadHistory() {
        List<String> history = HistoryManager.getHistory();
        historyListView.getItems().setAll(history);
    }

    @FXML
    private void handleClearHistory() {
        HistoryManager.clearHistory();
        historyListView.getItems().clear();
    }

    @FXML
    private void handleBack() throws IOException {
        CalculatorApp.setRoot("calculator");
    }
}
