package com.bluewhaleyt.common;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.whaleutils.R;

public class IntentUtil {

    private static Intent intent = new Intent();

    public static void intent(Activity activity, Class<?> targetActivity) {
        activity.startActivity(new Intent(activity, targetActivity));
        AnimationUtil.setIntentActivityEnterAnimation(activity);
    }

    public static void finishTransition(Activity activity) {
        AnimationUtil.setIntentActivityExitAnimation(activity);
    }

    public static void intentPutString(Activity activity, Class<?> targetActivity, String dataName, String dataValue) {
        intent = new Intent(activity,  targetActivity);
        intent.putExtra(dataName, dataValue);
        activity.startActivity(intent);
        AnimationUtil.setIntentActivityEnterAnimation(activity);
    }

    public static String intentGetString(Activity activity, String dataName) {
        return activity.getIntent().getExtras().getString(dataName);
    }

    public static void intentURL(Activity activity, String url) {
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
    }

    public static void intentApplicationInfoScreen(Activity activity) {
        intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }

    public static void intentInternalStorageScreen(Activity activity) {
        intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
//        activity.startActivityForResult(intent, 0);
        activity.startActivity(intent);
    }

    public static void intentDirectorySelectScreen(Activity activity) {
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        activity.startActivity(intent);
    }

    public static void intentFragment(FragmentActivity activity, Fragment fragment) {
        AnimationUtil.setIntentFragmentEnterExitAnimation(activity, fragment);
    }

}
