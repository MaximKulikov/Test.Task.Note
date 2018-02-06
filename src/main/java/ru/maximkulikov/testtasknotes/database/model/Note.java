package ru.maximkulikov.testtasknotes.database.model;

/**
 * Test.Task.Note
 * Created by maxim on 06.02.2018.
 */
public class Note {

    private int id;
    private String date;
    private String note;

    public Note(int id, String date, String note) {
        this.id = id;
        this.date = date;
        this.note = note;
    }

    public Note(String date, String note) {
        this.date = date;
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }
}
