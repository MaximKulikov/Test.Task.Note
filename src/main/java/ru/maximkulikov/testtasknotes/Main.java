package ru.maximkulikov.testtasknotes;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Test.Task.Note
 * Created by maxim on 05.02.2018.
 */
public class Main extends Application {
    public static void main(String[] args) {

        //Загружаем настройки из проперти
        Configuration.init();

        launch(args);
    }

    public static void exit(int i) {

        //Сохраняем проперти в файл
        Configuration.savePropertyToFile();

        Platform.exit();
        System.exit(i);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/list.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(this.getClass().getResource("/images/logo.png").toString()));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);

        String positionX = Configuration.getValue(Configuration.Param.WINDOW_LIST_X);
        String positionY = Configuration.getValue(Configuration.Param.WINDOW_LIST_Y);

        if (positionX != null && positionY != null) {
            try {
                double doubleX = Double.parseDouble(positionX);
                double doubleY = Double.parseDouble(positionY);

                stage.setX(doubleX);
                stage.setY(doubleY);

            } catch (NumberFormatException | NullPointerException e) {

                e.printStackTrace();
//              logger.error(e.getLocalizedMessage());
            }
        }

        stage.show();
    }
}
