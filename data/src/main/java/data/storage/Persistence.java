/*
 * Copyright 2015 RefineriaWeb
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package data.storage;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Save objects in disk and delete them too. It uses Gson as json parser.
 */
public class Persistence {
    private final RepositoryAdapter adapter;

    @Inject public Persistence(RepositoryAdapter adapter) {
        this.adapter = adapter;
    }

    /** Save in disk the object passed.
     * @param key the key whereby the object could be retrieved/deleted later. @see delete and @see retrieve.
     * @param data the object to be persisted.
     * */
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

    /** Delete the object previously saved.
     * @param key the key whereby the object could be deleted.
     * */
    public boolean delete(String key) {
        File file = new File(adapter.cacheDirectory(), key);
        return file.delete();
    }

    /** Retrieve the object previously saved.
     * @param key the key whereby the object could be retrieved.
     * @param clazz the type of class against the object need to be serialized
     * */
    public <T> T retrieve(String key, Class<T> clazz) {
        try {
            File file = new File(adapter.cacheDirectory(), key);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));

            T data = new Gson().fromJson(bufferedReader, clazz);

            bufferedReader.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Retrieve the object previously saved.
     * @param key the key whereby the object could be retrieved.
     * @param clazz the type of the class contained by the collection, not the collection itself
     * */
    public <T> List<T> retrieveCollection(String key, Class<T> clazz) {
        try {
            File file = new File(adapter.cacheDirectory(), key);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));

            Class<? extends Object[]> clazzArray = ((T[])Array.newInstance(clazz, 1)).getClass();
            Object[] array = new Gson().fromJson(bufferedReader, clazzArray);
            bufferedReader.close();

            List<T> data = new ArrayList<>();
            for (Object object : array) {
                data.add((T) object);
            }

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}