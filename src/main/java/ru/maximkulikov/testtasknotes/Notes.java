package ru.maximkulikov.testtasknotes;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.maximkulikov.testtasknotes.database.model.Note;

/**
 * Test.Task.Note
 * Created by maxim on 06.02.2018.
 */
public class Notes {
    private static ObservableList<Note> noteObservableList = FXCollections.observableArrayList();

    public static ObservableList<Note> getNoteObservableList() {
        return noteObservableList;
    }

    public static void addObservableNote(Note note) {
        noteObservableList.add(note);
    }

    public static void addListObservableNote(List<Note> list) {
        noteObservableList.addAll(list);
    }
}
