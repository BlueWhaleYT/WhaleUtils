package com.bluewhaleyt.whaleutils;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluewhaleyt.common.datasaving.DataStoreUtils;
import com.bluewhaleyt.common.datasaving.SharedPrefsUtils;

public class SampleJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_java);

        sharedPrefsSample();
    }

    private void sharedPrefsSample() {
        var et = (EditText) findViewById(R.id.et);
        var btnWrite = findViewById(R.id.btn_write);
        var btnGet = findViewById(R.id.btn_get);

        var key = "key_name";

        var sp = new SharedPrefsUtils(this, "pref_test");

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

    private void dataStoreSample() {
        var key = "key_name";
        var dataStore = new DataStoreUtils(this, "datastore_pref_test");
        var continuation = dataStore.getContinuation();

        dataStore.write(key, "123", dataStore.getContinuation());

        var data = dataStore.getWithFlow(key, "");
    }

}
