package ru.maximkulikov.testtasknotes.controllers;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.maximkulikov.testtasknotes.Configuration;
import ru.maximkulikov.testtasknotes.Main;
import ru.maximkulikov.testtasknotes.Notes;
import ru.maximkulikov.testtasknotes.database.DBFactory;
import ru.maximkulikov.testtasknotes.database.Databases;
import ru.maximkulikov.testtasknotes.database.model.Note;

/**
 * Test.Task.Note
 * Created by maxim on 05.02.2018.
 */
public class ListNote {

    @FXML
    public HBox dragField;
    @FXML
    public StackPane noteStage;
    @FXML
    public TableView<Note> tableNote;
    @FXML
    public TableColumn<Note, String> columnDate;
    @FXML
    public TableColumn<Note, String> columnNote;

    private Stage stage;
    private double xOffset;
    private double yOffset;

    /**
     * Кнопка добавления новой заметки
     *
     * @param actionEvent
     */
    @FXML
    public void addNewNote(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/newNote.fxml"));

        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(this.getClass().getResource("/images/logo.png").toString()));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(scene);

            Stage parentStage = (Stage) dragField.getScene().getWindow();
            stage.setX(parentStage.getX() + 60.0);
            stage.setY(parentStage.getY() + 120.0);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Закрытие окна с сохранением кординат
     *
     * @param event
     */
    @FXML
    public void closeApp(ActionEvent event) {

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Configuration.setParam(Configuration.Param.WINDOW_LIST_X, String.valueOf(stage.getX()));
        Configuration.setParam(Configuration.Param.WINDOW_LIST_Y, String.valueOf(stage.getY()));

        Main.exit(0);
    }

    /**
     * Заполняем таблицу
     * Загружаем данные из внешненей базы в фоне
     */
    @FXML
    void initialize() {

        columnDate.setCellValueFactory(new PropertyValueFactory<Note, String>("date"));
        columnNote.setCellValueFactory(new PropertyValueFactory<Note, String>("note"));

        tableNote.setItems(Notes.getNoteObservableList());

        new Thread(this::loadDataFromDB).start();

    }

    /**
     * Загружает данные в существующий список
     */
    private void loadDataFromDB() {

        Databases db = DBFactory.getDB(Configuration.getDB());

        List<Note> noteList = db.selectAllNotes();
        if (noteList != null) {
            Notes.addListObservableNote(noteList);
        }
    }

    @FXML
    public void minimizeApp(ActionEvent event) {

        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    /**
     * Перемещение окна
     *
     * @param event
     */
    public void paneOnMouseDragged(MouseEvent event) {

        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);

    }

    /**
     * Перемещение окна
     *
     * @param event
     */
    @FXML
    public void paneOnMousePressed(MouseEvent event) {
        stage = (Stage) dragField.getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }
}
