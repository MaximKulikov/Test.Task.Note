package ru.maximkulikov.testtasknotes.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.persistence.annotations.PrimaryKey;

/**
 * Test.Task.Note
 * Created by maxim on 06.02.2018.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(name = Note.ALL, query = "SELECT n FROM Note n")
@Table(name = "NOTES")
public class Note {

    public static final String ALL = "Note.ALL";

    @Id
    @Column
    @GeneratedValue
    private int id;

    @Column(name = "DateTime", nullable = true)
    private String date;

    @Column(name = "text")
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
}
