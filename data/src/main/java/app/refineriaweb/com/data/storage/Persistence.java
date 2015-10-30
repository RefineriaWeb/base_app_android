package app.refineriaweb.com.data.storage;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.inject.Inject;

public class Persistence {
    private final Context context;

    @Inject public Persistence(Context context) {
        this.context = context;
    }

    public boolean save(String key, Object data) {
        String wrapperJSONSerialized = new Gson().toJson(data);
        try {
            File file = new File(context.getFilesDir(), key);

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
        File file = new File(context.getFilesDir(), key);
        return file.delete();
    }

    @Nullable public <T> T retrieve(String key, Class<T> clazz) {
        try {
            File file = new File(context.getFilesDir(), key);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            return new Gson().fromJson(bufferedReader, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
