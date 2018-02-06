package ru.maximkulikov.testtasknotes.database.dao;

import java.util.List;
import ru.maximkulikov.testtasknotes.database.model.Note;

/**
 * Test.Task.Note
 * Created by maxim on 06.02.2018.
 */
public interface NoteDAO {

    int addNote(Note note);

    List<Note> selectAllNotes();

    void init();
}
