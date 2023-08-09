package com.bluewhaleyt.whaleutils.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluewhaleyt.common.common.CommonExtKt;
import com.bluewhaleyt.design.widget.recyclerview.adapter.AdapterCallback;
import com.bluewhaleyt.design.widget.recyclerview.adapter.CustomAdapter;
import com.bluewhaleyt.design.widget.recyclerview.adapter.FilterMode;
import com.bluewhaleyt.network.okhttp.OkHttpUtils;
import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.activity.playground.system.SystemInfo;
import com.bluewhaleyt.whaleutils.databinding.ActivityJavaTestBinding;

import java.util.ArrayList;

public class JavaTestActivity extends AppCompatActivity {

    private ActivityJavaTestBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJavaTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        var list = new ArrayList<SystemInfo>();

        list.add(new SystemInfo("Alan", "Blueberry"));
        list.add(new SystemInfo("Steve", "Apple"));
        list.add(new SystemInfo("Mandy", "Orange"));
        list.add(new SystemInfo("Alex", "Banana"));
        list.add(new SystemInfo("Heidi", "Watermelon"));
        list.add(new SystemInfo("Jacky", "Kiwi"));

        var callback = new AdapterCallback<SystemInfo>() {
            @Override
            public int getItemViewType(SystemInfo data, int itemIndex) {
                return 0;
            }

            @Override
            public void onItemLongClick(@NonNull View itemView, SystemInfo data, int itemIndex) {

            }

            @Override
            public void onItemClick(@NonNull View itemView, SystemInfo data, int itemIndex) {
                Toast.makeText(JavaTestActivity.this, data.getValue(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCreateView(@NonNull View itemView, SystemInfo data, int itemIndex) {
                TextView tv1 = itemView.findViewById(R.id.tv_text_1);
                TextView tv2 = itemView.findViewById(R.id.tv_text_2);

                var query = binding.etSearch.getText().toString();
                var color = 0xFF3A4FF1;

                tv1.setText(CommonExtKt.highlightText(data.getName(), query, color));
                tv2.setText(CommonExtKt.highlightText(data.getValue(), query, color));
            }
        };
        var adapter = new CustomAdapter<SystemInfo>(this);
        adapter.setCallback(callback)
                .setLayoutView(R.layout.layout_list_item_1)
                .setFilterable(true);

        binding.rv.setAdapter(adapter, list);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.addFilter(FilterMode.SIMPLE,charSequence, "name");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
