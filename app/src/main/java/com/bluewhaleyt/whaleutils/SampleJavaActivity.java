package com.bluewhaleyt.whaleutils;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluewhaleyt.file_management.basic.extension.FileExtKt;
import com.bluewhaleyt.file_management.basic.utils.FileUtils;
import com.bluewhaleyt.file_management.saf.extension.SAFExtKt;
import com.bluewhaleyt.file_management.saf.utils.SAFUtils;

public class SampleJavaActivity extends AppCompatActivity {

    FileUtils fileUtils = new FileUtils();
    SAFUtils safUtils = new SAFUtils(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_java);

        TextView tvUri = findViewById(R.id.tv_uri);
        TextView tvPath = findViewById(R.id.tv_path);

        if (fileUtils.isGrantedExternalStorageAccess()) {
            safUtils.registerActivityResultLauncher(this, uri -> {
                tvUri.setText(uri.toString());
                tvPath.setText(FileExtKt.toRealFilePath(uri, this));
                return null;
            }).launch(safUtils.getIntentOpenDocument());
        } else safUtils.requestAllFileAccess();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        safUtils.setPermanentAccess(data);
    }
}
