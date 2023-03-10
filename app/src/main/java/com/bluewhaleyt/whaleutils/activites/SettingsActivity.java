package com.bluewhaleyt.whaleutils.activites;

import androidx.annotation.NonNull;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.whaleutils.activites.components.WhaleUtilsActivity;
import com.bluewhaleyt.whaleutils.fragments.preferences.SettingsFragment;

public class SettingsActivity extends WhaleUtilsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goToPreviousFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }

    private void goToPreviousFragment() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}