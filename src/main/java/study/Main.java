package study;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import study.db.Init;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Инициализация таблицы*/
        Init.createDb();
        /*Загрузка View*/
        Parent root = FXMLLoader.load(getClass().getResource("/calendar.fxml"));
        Scene scene = new Scene(root, 995, 575);
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


