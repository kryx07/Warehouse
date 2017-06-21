package com.example.sda.warehouse.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.utils.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class NoteActivity extends AppCompatActivity {
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final String NOTE_FILE = "note.txt";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_EXTERNAL_STORAGE);

        editText = (EditText) findViewById(R.id.note);

        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();

                if (ContextCompat.checkSelfPermission(NoteActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(NoteActivity.this, R.string.cant_save, Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }

                /*try {
                    FileOutputStream fos = openFileOutput(NOTE_FILE, Context.MODE_PRIVATE);
                    fos.write(text.getBytes());
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                if(!MyApplication.isExternalStorageWritable()) {

                    return;
                }

                File documents = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

                logDebug(documents.toString());
                documents.mkdirs();

                File noteFile = new File(documents, NOTE_FILE);
                
                try {
                    noteFile.createNewFile();
                    FileWriter writer = new FileWriter(noteFile);
                    writer.write(text);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*try {
            FileInputStream fis = openFileInput(NOTE_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String text = "";

            String temp;

            do {
                temp = br.readLine();

                if(temp != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        text += temp + System.lineSeparator();
                    } else {
                        text += temp + '\n';
                    }
                }
            } while(temp != null);

            editText.setText(text);

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        File documents = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File noteFile = new File(documents, NOTE_FILE);

        try {
            FileReader reader = new FileReader(noteFile);
            BufferedReader br = new BufferedReader(reader);

            String text = "";

            String temp;

            do {
                temp = br.readLine();

                if(temp != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        text += temp + System.lineSeparator();
                    } else {
                        text += temp + '\n';
                    }
                }
            } while(temp != null);

            editText.setText(text);

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logDebug(String string) {
        Log.e(getClass().getSimpleName(), string);

    }
}
