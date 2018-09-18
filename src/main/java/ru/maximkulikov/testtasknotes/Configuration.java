package ru.maximkulikov.testtasknotes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

/**
 * Test.Task.Note
 * Created by maxim on 05.02.2018.
 */
public class Configuration {

    private static Properties config = new Properties();

    public static boolean init() {

        try {
            File file = new File("config.properties");
            file.createNewFile();
//          log.info("File created: {}", isfilecreated);


            try (InputStreamReader input = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
                config.load(input);
            }

        } catch (IOException ex) {
//          log.error(ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public static String getValue(Param param) {

        return config.getProperty(param.toString());
    }

    public static boolean savePropertyToFile() {

        try(OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream("config.properties"), "UTF-8")) {
            config.store(output,"# Test.Task.Note");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setParam(Param param, String value) {
        config.put(param.toString(),value);
    }

    /**
     * Получение типа используемой БД.
     * @return default= sqlite
     */
    public static String getDB() {

        return config.getProperty(Param.DB_TYPE.toString(), "sqlite");
    }

    public static enum Param {
        WINDOW_LIST_X,
        WINDOW_LIST_Y,
        DB_TYPE
    }
}
