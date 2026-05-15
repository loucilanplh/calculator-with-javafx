package calculator.Controller;

import calculator.CalculatorApp;
import calculator.service.HistoryManager;
import calculator.service.ThemeManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;

public class HistoryController {

    @FXML
    private ListView<String> historyListView;

    @FXML
    private StackPane rootPane;

    @FXML
    private Button themeToggleBtn;

    @FXML
    public void initialize() {
        ThemeManager.setTheme(rootPane);
        updateThemeIcon();
        loadHistory();
        setupCustomCell();
    }

    @FXML
    private void toggleTheme() {
        ThemeManager.toggleTheme(rootPane);
        updateThemeIcon();
    }

    private void updateThemeIcon() {
        themeToggleBtn.setText(ThemeManager.isLightMode() ? "🌙" : "☀️");
    }

    private void setupCustomCell() {
        historyListView.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    String[] parts = item.split(" = ");
                    String expression = parts.length > 0 ? parts[0] : "";
                    String result = parts.length > 1 ? parts[1] : "";

                    VBox card = new VBox(5);
                    card.getStyleClass().add("history-card");
                    
                    Label expLabel = new Label("EXPRESSION");
                    expLabel.getStyleClass().add("card-label");
                    
                    Label expValue = new Label(expression);
                    expValue.getStyleClass().add("card-expression");
                    
                    Label resLabel = new Label("RESULT");
                    resLabel.getStyleClass().add("card-label");
                    
                    Label resValue = new Label(result);
                    resValue.getStyleClass().add("card-result");

                    card.getChildren().addAll(expLabel, expValue, resLabel, resValue);
                    setGraphic(card);
                    setStyle("-fx-background-color: transparent; -fx-padding: 10 0;");
                }
            }
        });
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
