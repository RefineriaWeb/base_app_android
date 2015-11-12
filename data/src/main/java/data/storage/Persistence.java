package data.storage;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.inject.Inject;

public class Persistence {
    private final RepositoryAdapter adapter;

    @Inject public Persistence(RepositoryAdapter adapter) {
        this.adapter = adapter;
    }

    public boolean save(String key, Object data) {
        String wrapperJSONSerialized = new Gson().toJson(data);
        try {
            File file = new File(adapter.cacheDirectory(), key);

            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(wrapperJSONSerialized);
            fileWriter.flush();
            fileWriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String key) {
        File file = new File(adapter.cacheDirectory(), key);
        return file.delete();
    }

    public <T> T retrieve(String key, Class<T> clazz) {
        try {
            File file = new File(adapter.cacheDirectory(), key);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            return new Gson().fromJson(bufferedReader, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
