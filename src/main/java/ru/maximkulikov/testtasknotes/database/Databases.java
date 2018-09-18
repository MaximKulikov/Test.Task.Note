package ru.maximkulikov.testtasknotes.database;

import java.util.List;

import ru.maximkulikov.testtasknotes.database.model.Note; /**
 * Test.Task.Note
 * Created by maxim on 19.03.2018.
 */
public interface Databases {
    int addNote(Note note);

    void init();

    List<Note> selectAllNotes();
}
