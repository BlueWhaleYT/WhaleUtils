package com.bluewhaleyt.whaleutils;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.bluewhaleyt.common.datasaving.SharedPrefsUtils;
import com.bluewhaleyt.file_management.basic.extension.FileExtKt;
import com.bluewhaleyt.file_management.basic.utils.FileUtils;
import com.bluewhaleyt.file_management.saf.extension.SAFExtKt;
import com.bluewhaleyt.file_management.saf.utils.SAFUtils;

import kotlin.jvm.functions.Function1;

public class SampleJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_java);

        sharedPrefsSample();
    }

    private void sharedPrefsSample() {
        EditText et = findViewById(R.id.et);
        Button btnWrite = findViewById(R.id.btn_write);
        Button btnGet = findViewById(R.id.btn_get);

        String key = "key_name";

        SharedPrefsUtils sp = new SharedPrefsUtils(this, "pref_test");

        btnWrite.setOnClickListener(v -> {
            String value = et.getText().toString();
            sp.write(key, value);
        });

        btnGet.setOnClickListener(v -> {
            String getValue = sp.get(key, "");
            Toast.makeText(this, getValue, Toast.LENGTH_SHORT).show();
        });

        Log.d("pref", sp.listPreferences().toString());

    }

}
