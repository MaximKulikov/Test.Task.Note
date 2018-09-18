package ru.maximkulikov.testtasknotes.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import ru.maximkulikov.testtasknotes.database.model.Note;

/**
 * Test.Task.Note
 * Created by maxim on 19.03.2018.
 */
public class LiquibaseImp implements Databases {

    private static final String DB_URL = "jdbc:derby:directory:db/notes;create=true";
    private static final String CHANGELOG = "liquibase/db.changelog-master.xml";

    private EntityManager entityManager;

    @Override
    public int addNote(Note note) {
        entityManager.getTransaction().begin();
        entityManager.persist(note);
        entityManager.getTransaction().commit();
        if (entityManager.contains(note)) {
            return 1;
        }
        return 0;
    }

    @Override
    public void init() {
        entityManager = Persistence.createEntityManagerFactory("notes").createEntityManager();
    }

    @Override
    public List<Note> selectAllNotes() {
        List<Note> notesList = entityManager.createNamedQuery(Note.ALL, Note.class)
                .getResultList();
        return notesList;
    }
}
