package ru.maximkulikov.testtasknotes.database.dao.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.sqlite.SQLiteDataSource;
import ru.maximkulikov.testtasknotes.database.dao.NoteDAO;
import ru.maximkulikov.testtasknotes.database.model.Note;

/**
 * Test.Task.Note
 * Created by maxim on 06.02.2018.
 */
public class SQLiteNoteDAO implements NoteDAO {

    private SQLiteDataSource ds = new SQLiteDataSource();

    private Connection conn;

    public SQLiteNoteDAO() {
        ds.setDatabaseName(":notes:");
        ds.setUrl("jdbc:sqlite:notes.db");
    }

    /**
     * Добавляем заметку
     *
     * @param note
     * @return
     */
    @Override
    public int addNote(Note note) {

        String sql = "INSERT INTO note (date, text) VALUES (?,?);";

        int result = 0;

        try(Connection c = getConn()) {

            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1,note.getDate());
            ps.setString(2,note.getNote());

            result = ps.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }

        return result;
    }

    /**
     * Получаем все заметки при запуске программы
     * @return
     */
    @Override
    public List<Note> selectAllNotes() {

        String sql = "SELECT * FROM note;";

        try(PreparedStatement ps = getConn().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            List<Note> notes = new ArrayList<>();
            while (rs.next()) {
                notes.add(new Note(rs.getInt("id"), rs.getString("date"), rs.getString("text")));
            }
            return notes;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Создаем БД и размечаем таблицу
     */
    @Override
    public void init() {

        File db = new File("notes.db");

        if (!db.exists()) {
            try {
                db.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            createTableNote();

        }
    }

    private void createTableNote() {

        String sql = "CREATE TABLE IF NOT EXISTS note (id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, text TEXT);";

        try (Statement stmt = getConn().createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Connection getConn() throws SQLException {

        if (conn != null && !conn.isClosed()) {
            return conn;
        } else {

            conn = ds.getConnection();
            return conn;
        }
    }

}
