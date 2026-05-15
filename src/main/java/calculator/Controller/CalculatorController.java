package calculator.Controller;

import calculator.CalculatorApp;
import calculator.service.CalculatorEngine;
import calculator.service.FractionFormatter;
import calculator.service.HistoryManager;
import calculator.service.ThemeManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class CalculatorController {

    @FXML
    private Label historyDisplay;
    
    @FXML
    private TextField mainDisplay;

    @FXML
    private StackPane rootPane;

    @FXML
    private Button themeToggleBtn;

    private boolean isFractionMode = false;
    private double lastResult = 0;
    private boolean isResultShowing = false;

    @FXML
    public void initialize() {
        ThemeManager.setTheme(rootPane);
        updateThemeIcon();
    }

    @FXML
    private void toggleTheme() {
        boolean isLight = ThemeManager.toggleTheme(rootPane);
        updateThemeIcon();
    }

    private void updateThemeIcon() {
        themeToggleBtn.setText(ThemeManager.isLightMode() ? "🌙" : "☀️");
    }

    @FXML
    private void handleAction(ActionEvent event) {
        Button source = (Button) event.getSource();
        String text = source.getText();
        
        if (isResultShowing) {
            // If it's an operator or percentage, allow appending to the result
            if ("+-x÷%xⁿ".contains(text)) {
                isResultShowing = false;
            } else {
                mainDisplay.clear();
                isResultShowing = false;
            }
        }
        
        switch (text) {
            case "x": mainDisplay.appendText("*"); break;
            case "÷": mainDisplay.appendText("/"); break;
            case "π": mainDisplay.appendText("pi"); break;
            case "xⁿ": mainDisplay.appendText("^"); break;
            case "lg": mainDisplay.appendText("log("); break;
            case "√": mainDisplay.appendText("sqrt("); break;
            case "1/x": mainDisplay.appendText("1/("); break;
            case "sin":
            case "cos":
            case "tan":
            case "log":
            case "ln":
            case "sqrt":
                mainDisplay.appendText(text + "(");
                break;
            default:
                mainDisplay.appendText(text);
                break;
        }
    }

    @FXML
    private void handleClear() {
        mainDisplay.clear();
        historyDisplay.setText("");
        isResultShowing = false;
    }

    @FXML
    private void handleBackspace() {
        if (isResultShowing) {
            handleClear();
            return;
        }
        String currentText = mainDisplay.getText();
        if (!currentText.isEmpty()) {
            mainDisplay.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    @FXML
    private void handleCalculate() {
        String expression = mainDisplay.getText();
        if (expression == null || expression.isEmpty()) return;

        try {
            double result = CalculatorEngine.evaluate(expression);
            lastResult = result;
            historyDisplay.setText(expression + " =");
            
            String formattedResult = formatResult(result);
            mainDisplay.setText(formattedResult);
            isResultShowing = true;

            HistoryManager.saveCalculation(expression, formattedResult);
        } catch (Exception e) {
            mainDisplay.setText("Error");
            isResultShowing = true;
        }
    }

    @FXML
    private void handleFraction() {
        isFractionMode = !isFractionMode;
        if (isResultShowing) {
            mainDisplay.setText(formatResult(lastResult));
        }
    }

    private String formatResult(double result) {
        if (isFractionMode) {
            return FractionFormatter.toFraction(result);
        } else {
            if (result == (long) result) {
                return String.format("%d", (long) result);
            } else {
                return String.format("%s", result);
            }
        }
    }

    @FXML
    private void showHistory() throws IOException {
        CalculatorApp.setRoot("history");
    }
}
