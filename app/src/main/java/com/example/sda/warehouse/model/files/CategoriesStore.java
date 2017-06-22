package com.example.sda.warehouse.model.files;

import android.content.Context;
import android.util.Log;

import com.example.sda.warehouse.model.IStore;
import com.example.sda.warehouse.model.beans.Categories;
import com.example.sda.warehouse.model.beans.Category;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CategoriesStore implements IStore<Category> {

    private String fileName;
    private Context context;

    private static String DESC = "DESC";
    private static String ASC = "ASC";

    public CategoriesStore(String fileName, Context context) {
        this.fileName = fileName;
        this.context = context;
    }


    @Override
    public List<Category> getAll() {
        return getAll("id", ASC);
    }

    @Override
    public List<Category> getAll(String column, final String order) {

        Gson gson = new Gson();
        Categories categories = gson.fromJson(readFile(), Categories.class);

        if (categories != null) {
            return categories.getCategoryList();
        }
        return new Categories(new ArrayList<Category>()).getCategoryList();

        /*List<Category> categoryList = new ArrayList<>();
        Category[] categories = gson.fromJson(readFile(), Category[].class);
        if (categories != null) {
            categoryList = new ArrayList<>(Arrays.asList(gson.fromJson(readFile(), Category[].class)));
        }

        logDebug(categoryList.toString());
        return categoryList;*/

    }

    @Override
    public Category getById(long id) {
        return null;
    }

    @Override
    public List<Category> getWithoutId(long withoutId) {
        List<Category> categoryList = getAll();
        for (Category c : categoryList) {
            if (c.getId() == withoutId) {
                categoryList.remove(c);
                break;
            }

        }
        return categoryList;
    }

    @Override
    public Category getEmpty() {
        return new Category(0, "Empty", null);
    }

    @Override
    public void add(Category item) {

        long autoIncrement = getAutoIncrement();

        List<Category> categoryList = getAll();
        item.setId(autoIncrement);
        categoryList.add(item);
        ++autoIncrement;

        saveToFile(autoIncrement, categoryList);


    }

    @Override
    public void update(Category item) {
        List<Category> categoryList = getAll();

        for (Category c1 : categoryList) {
            if (c1.getId() == c1.getId()) {
                c1.setName(c1.getName());
                c1.setParentCategory(c1.getParentCategory());

                saveToFile(getAutoIncrement(), categoryList);
                return;
            }
        }
    }

    @Override
    public void remove(long id) {
        List<Category> categoryList = getAll();

        for (Category c1 : categoryList) {
            if (c1.getId() == c1.getId()) {
                categoryList.remove(c1);

                saveToFile(getAutoIncrement(), categoryList);
                return;
            }
        }
    }

    private String readFile() {
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String content = FileHelper.getContent(bufferedReader);
            bufferedReader.close();
            return content;

        } catch (IOException e) {
            logDebug(e.getMessage());
            return "";

        }
    }

    private void saveToFile(long autoIncrement, List<Category> categoryList) {

        Gson gson = new Gson();
        Categories categories = new Categories(categoryList, autoIncrement);
        String content = gson.toJson(categories);

        logDebug("POJO to JSON: " + content);

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private long getAutoIncrement() {
        /*String content = readFile();
        int autoIncrement;

        try {
            JSONObject jsonObject = new JSONObject(content);
            autoIncrement = jsonObject.getInt("auto_increment");
        } catch (JSONException e) {
            logDebug(e.getMessage());
            autoIncrement = 1;
        }
        return autoIncrement;*/

        String context = readFile();
        Gson gson = new Gson();
        Categories categories = gson.fromJson(readFile(), Categories.class);
        return categories == null ? 1 : categories.getNextId();

    }


    private void logDebug(String string) {
        Log.e(getClass().getSimpleName(), string);

    }
}
