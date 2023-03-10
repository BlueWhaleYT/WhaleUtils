package com.bluewhaleyt.whaleutils.fragments.preferences;

import android.os.Bundle;

import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.component.preferences.CustomPreferenceFragment;
import com.bluewhaleyt.component.preferences.material3.Material3ButtonPreference;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.debug.Constants;
import com.bluewhaleyt.whaleutils.R;

public class LanguageFragment extends CustomPreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_language, rootKey);
        init();
    }

    private void init() {
        try {

            DynamicColorsUtil dynamicColors = new DynamicColorsUtil(requireActivity());

            var componentBtnTest = findPreference("component_btn_test");

            componentBtnTest.setOnPreferenceClickListener(preference -> {
                IntentUtil.intentURL(requireActivity(), Constants.PROJECT_CROWDIN_TRANSLATION_URL);
                return true;
            });

        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(getActivity(), e.getMessage(), e.toString());
        }

    }
}