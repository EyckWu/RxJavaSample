package com.eyck.rxjavademo.module.cache_5.data;

import com.eyck.rxjavademo.App;
import com.eyck.rxjavademo.model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * Created by Eyck on 2017/9/11.
 */

public class Database {
    private static String DATA_FILE_NAME = "data.db";

    private static Database INSTANCE;

    File dataFile = new File(App.getInstance().getFilesDir(),DATA_FILE_NAME);
    Gson gson = new Gson();

    private Database() {
    }

    public static Database getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    public List<Item> readItems(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Reader reader = new FileReader(dataFile);
            return gson.fromJson(reader,new TypeToken<List<Item>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeItems(List<Item> items){
        String json = gson.toJson(items);
        if(!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Writer writer = null;
            try {
                writer = new FileWriter(dataFile);
                writer.write(json);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete() {
        dataFile.delete();
    }
}
