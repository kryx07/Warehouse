package com.example.sda.warehouse.model.files;

import android.content.Context;
import android.util.Log;

import com.example.sda.warehouse.model.IStore;
import com.example.sda.warehouse.model.beans.Category;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
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
       /* String content = readFile();
        List<Category> categoryList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(content);
            JSONArray categories = jsonObject.getJSONArray("data");
            for (int i = 0; i < categories.length(); ++i) {
                JSONObject category = (JSONObject) categories.get(i);
                categoryList.add(new Category(category));
            }
        } catch (JSONException e) {
            logDebug(e.getMessage());
        }

        Collections.sort(categoryList, new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return order.equals(ASC) ?
                        Long.compare(o1.getId(), o2.getId()) :
                        -Long.compare(o1.getId(), o2.getId());
            }
        });

        return categoryList;*/
        List<Category> list = new ArrayList<Category>();
        list.add(getEmpty());
        return list;

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

        String s = gson.toJson(categoryList);

        logDebug(s);
      /*  try {
            JSONObject root = new JSONObject();
            root.put("auto_increment", autoIncrement);

            JSONArray categories = new JSONArray();

            for (Category c : categoryList) {
                JSONObject o = new JSONObject();
                o.put("id", c.getId());
                o.put("name", c.getName());

                if (c.getParentCategory() != null) {
                    o.put("parent_category", c.getParentCategory());
                }

                categories.put(o);
            }

            root.put("data", categories);

            String content = root.toString();

            try {
                FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                fos.write(content.getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            Log.d("CategoriesStore", e.getMessage());
        }*/
    }

    private long getAutoIncrement() {
        String content = readFile();
        int autoIncrement;

        try {
            JSONObject jsonObject = new JSONObject(content);
            autoIncrement = jsonObject.getInt("auto_increment");
        } catch (JSONException e) {
            logDebug(e.getMessage());
            autoIncrement = 1;
        }
        return autoIncrement;
    }


    private void logDebug(String string) {
        Log.e(getClass().getSimpleName(), string);

    }
}
