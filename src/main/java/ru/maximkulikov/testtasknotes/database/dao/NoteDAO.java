package ru.maximkulikov.testtasknotes.database.dao;

import java.util.List;

import ru.maximkulikov.testtasknotes.database.Databases;
import ru.maximkulikov.testtasknotes.database.model.Note;

/**
 * Test.Task.Note
 * Created by maxim on 06.02.2018.
 */
public interface NoteDAO extends Databases {

    List<Note> selectAllNotes();
}
