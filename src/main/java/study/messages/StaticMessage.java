package study.messages;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class StaticMessage {

    private static Stage stage;
    private static Alert alert;

    /*Вывод ошибки*/
    public static void createErrorAlertDialog(String message) {
        alert = new Alert(Alert.AlertType.ERROR);
        String title_for_alert = "Ошибка!";
        alert.setTitle(title_for_alert);
        alert.setHeaderText("Ошибка");
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setStyle("-fx-font-family: Segoe UI; -fx-font-size: 16;-fx-font-weight: Bold;");
        if(stage != null)
        alert.showAndWait();
    }
    /*Вывод информации*/
    public static void createInfodialog(String message) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        String title_for_alert = "Добавление новых данных в базу!";
        alert.setTitle(title_for_alert);
        alert.setHeaderText("Запись добавлена!");
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setStyle("-fx-font-family: Segoe UI; -fx-font-size: 16;-fx-font-weight: Bold;");
        alert.showAndWait();
    }



}
