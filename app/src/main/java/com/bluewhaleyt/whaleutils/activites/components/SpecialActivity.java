package com.bluewhaleyt.whaleutils.activites.components;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.common.SDKUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.whaleutils.App;
import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.tools.PreferencesManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SpecialActivity extends AppCompatActivity {

    public App app;

    private AlertDialog dialog;

    private DynamicColorsUtil dynamicColors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        app = App.getInstance();

//        showLoadingDialog();
//
//        final Handler handler = new Handler();
//        handler.postDelayed(this::init, 2000);
//
//        handler.postDelayed(() -> dialog.dismiss(), 2015);

        init();
    }

    private void showLoadingDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_layout_loading, null);
        dialog = new MaterialAlertDialogBuilder(this).create();
        dialog.setView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void init() {

        dynamicColors = new DynamicColorsUtil(this);

        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
        CommonUtil.setToolBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);

        switch (PreferencesManager.getAppTheme()) {
            case "auto":
                if (SDKUtil.isAtLeastSDK29()) {
                    App.getInstance().updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                } else {
                    App.getInstance().updateTheme(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }
                break;
            case "light":
                App.getInstance().updateTheme(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                App.getInstance().updateTheme(AppCompatDelegate.MODE_NIGHT_YES);
                break;

        }

    }

}
