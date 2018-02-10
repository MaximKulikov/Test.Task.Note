package ru.maximkulikov.testtasknotes.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.maximkulikov.testtasknotes.Configuration;
import ru.maximkulikov.testtasknotes.Notes;
import ru.maximkulikov.testtasknotes.database.DBFactory;
import ru.maximkulikov.testtasknotes.database.dao.NoteDAO;
import ru.maximkulikov.testtasknotes.database.model.Note;

/**
 * Test.Task.Note
 * Created by maxim on 06.02.2018.
 */
public class NewNote {

    @FXML
    public JFXDatePicker datePicker;
    @FXML
    public JFXTimePicker timePicker;
    @FXML
    public TextField notePicker;
    @FXML
    public Label labelWarning;
    @FXML
    public JFXButton buttonAddNote;
    @FXML
    public Label count;

    private ReadOnlyIntegerProperty countProperty;
    private BooleanProperty buttonDisableState = new SimpleBooleanProperty(false);



    /**
     * Оборачиваем оповещение о результате добавления заметки в базу данных в потом JavaFX
     *
     * @param text
     * @param color
     */
    private void addNoteWithDBResultDescriptionFromCommonThread(String text, String color) {
        Platform.runLater(() -> textWarningOn(text, color));
    }

    /**
     * Клик по кнопке добавления новой заметки
     *
     * @param actionEvent
     */
    @FXML
    public void buttonAddNote_OnClick(ActionEvent actionEvent) {

        buttonDisableState.setValue(true);
        System.out.println(buttonAddNote.disableProperty().toString());

        boolean error = false;
        LocalDate date = datePicker.getValue();

        if (date == null) {
            error = true;
            textWarningOn("Дата не выбрана", "red");
        }

        LocalTime time = timePicker.getValue();

        if (time == null) {
            error = true;
            textWarningOn("Время не выбрано", "red");
        }
        String text = notePicker.getText();

        if (text.length() == 0) {
            error = true;
            textWarningOn("Заметка не может быть пустой!", "red");

        }

        if (error) {

            new Thread(() ->
            {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {

                    textWarningOff();
                });
            }
            ).start();

        } else {

            NoteDAO db = DBFactory.getDB(Configuration.getDB());

            if (db == null) {
                labelWarning.setText("Ошибка подключения к базе, проверьте файл конфигурации");
            }

            StringBuilder dateTime = new StringBuilder();

            dateTime.append(date.getDayOfMonth()).append(".").append(date.getDayOfMonth()).append(".").append(date.getYear());
            dateTime.append(" ").append(time.getHour()).append(":").append(time.getMinute()).append(":").append(time.getSecond());

            Note note = new Note(dateTime.toString(), text);

            labelWarning.setText("Добавляю заметку в базу");

            /**
             * Добавляем данные в базу в отдельном потоке
             */
            new Thread(() -> {

                switch (db.addNote(note)) {
                    case 0:
                        addNoteWithDBResultDescriptionFromCommonThread("Произошла непредвиденная ошибка", "red");
                        buttonDisableState.setValue(false);
                        break;
                    case 1:
                        addNoteWithDBResultDescriptionFromCommonThread("Заметка добавлена", "green");
                        Notes.addObservableNote(note);
                        closeWindowIn(2500);
                        break;
                    case 2:
                        addNoteWithDBResultDescriptionFromCommonThread("Заметка не добавлена", "red");
                        buttonDisableState.setValue(false);
                        break;
                }

            }).start();

        }

    }

    @FXML
    public void closeApp(ActionEvent actionEvent) {

        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

    }

    private void closeWindowIn(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            labelWarning.getScene().getWindow().hide();

        });
    }

    @FXML
    void initialize() {

        datePicker.setValue(LocalDate.now());
        timePicker.setValue(LocalTime.now());

        countProperty= notePicker.lengthProperty();
        count.textProperty().bind(countProperty.asString());

        BooleanBinding buttonState = countProperty.greaterThan(100).or(buttonDisableState);

        buttonAddNote.disableProperty().bind(buttonState);


        notePicker.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 100) {
                textWarningOn("Текст превышает 100 символов", "red");
            } else {
                textWarningOff();
            }
        });

    }

    private void textWarningOff() {
        //buttonAddNote.setDisable(false);
        labelWarning.setStyle(null);
        labelWarning.setText("");
    }

    private void textWarningOn(String text, String color) {
        //buttonAddNote.setDisable(true);
        labelWarning.setText(text);
        labelWarning.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white");
    }

}
