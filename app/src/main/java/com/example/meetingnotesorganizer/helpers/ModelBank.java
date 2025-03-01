package com.example.meetingnotesorganizer.helpers;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.meetingnotesorganizer.data.Model;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ModelBank<T extends Model> {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String modelKey;
    private Class<T> modelClass;

    private static Gson gson = new Gson();

    public ModelBank(SharedPreferences sharedPref, SharedPreferences.Editor editor, String modelKey, Class<T> modelClass) {
        this.sharedPref = sharedPref;
        this.editor = editor;
        this.modelKey = modelKey;
        this.modelClass = modelClass;
    }

    public List<T> getAll() {
        // TODO: Implement this and study how it works from within
        return new ArrayList<>();
    }

    public T get(int id) {
        for (T model : getAll()) {
            if (model.getId() == id) {
                return model;
            }
        }
        return null;
    }

    public void add(T model) {
        List<T> models = getAll();
        model.setId(getLatestId());
        models.add(model);
        save(models);
    }

    public void update(T updatedModel) {
        List<T> models = getAll();
        for (int i = 0; i < models.size(); i++) {
            T model = models.get(i);
            if (model.getId() == updatedModel.getId()) {
                models.set(i, updatedModel);
                save(models);
                return;
            }
        }
    }

    public void delete(int id) {
        List<T> models = getAll();
        models.removeIf(model -> model.getId() == id);
        save(models);
    }

    public void save(List<T> models) {
        // TODO: Implement this and study how it works from within
    }

    public void log() {
        for (T model : getAll()) {
            Log.e("DATA", model.toString());
        }
    }

    private int getLatestId() {
        int maxId = 0;
        for (T model : getAll()) {
            if (model.getId() > maxId) {
                maxId = model.getId();
            }
        }
        return maxId + 1;
    }
}
