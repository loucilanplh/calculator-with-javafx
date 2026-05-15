package calculator.service;

import javafx.scene.Parent;

public class ThemeManager {
    private static boolean isLightMode = false;

    public static void setTheme(Parent root) {
        if (isLightMode) {
            root.getStyleClass().add("light-theme");
        } else {
            root.getStyleClass().remove("light-theme");
        }
    }

    public static boolean toggleTheme(Parent root) {
        isLightMode = !isLightMode;
        setTheme(root);
        return isLightMode;
    }

    public static boolean isLightMode() {
        return isLightMode;
    }
}
