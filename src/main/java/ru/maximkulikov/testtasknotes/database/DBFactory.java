package ru.maximkulikov.testtasknotes.database;

import java.util.HashMap;
import java.util.Map;
import ru.maximkulikov.testtasknotes.database.dao.NoteDAO;
import ru.maximkulikov.testtasknotes.database.dao.impl.SQLiteNoteDAO;

/**
 * Test.Task.Note
 * Created by maxim on 06.02.2018.
 */
public class DBFactory {

    //Фабрика Баз Данных
    private static Map<String, NoteDAO> mapFactory = new HashMap();

    //Метка для проверки необходимости инициализировать базу
    private static Map<String,Boolean> init = new HashMap<>();

    static {

        mapFactory.put("sqlite", new SQLiteNoteDAO());
        init.put("sqlite", true);
    }

    public static NoteDAO getDB(String dbType) {

        /**
         * Инициализируем базу, если необходимо
         */
        Boolean requreInit = init.get(dbType);
        if (requreInit != null && requreInit) {
            mapFactory.get(dbType).init();
            init.put(dbType,false);
        }

        return mapFactory.get(dbType);
    }

}
