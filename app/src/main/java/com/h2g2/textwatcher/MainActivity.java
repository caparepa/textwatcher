package com.h2g2.textwatcher;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;

import com.h2g2.textwatcher.databinding.ActivityMainBinding;

import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    EditText et;
    Locale currentLocale;
    String languageCode;

    private static final Set<String> troubleLanguages = new HashSet<>(Arrays.asList(
            new String[]{"ar"}
    ));

    private final String numbers = "1234567890";
    private String acceptedDigits;
    private DecimalFormatSymbols localDecimalFormatSymbols;
    private char localDecimalSeparator;
    private char localGroupingSeparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        languageCode = "en";
        et = binding.numberField;

        LanguageManager.saveLanguage(languageCode, this);
        updateLanguageOptions();

        String inputInit = "0" + localDecimalSeparator + "00";
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(16);
        et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et.setFilters(filters);
        et.setText(inputInit);
        et.setSelection(inputInit.length());

        final NumberInputTextWatcher itw = new NumberInputTextWatcher(et, localDecimalSeparator, localGroupingSeparator, currentLocale);
        et.addTextChangedListener(new NumberInputTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int after) {
                itw.onTextChanged(s, start, before, after);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                itw.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void afterTextChanged(Editable s) {
                itw.afterTextChanged(s);
            }
        });
    }

    private void updateLanguageOptions() {
        currentLocale = LanguageManager.getCurrentLocale(this);
        if (troubleLanguages.contains(currentLocale.getLanguage())) {
            currentLocale = new Locale("es");
        }
        localDecimalFormatSymbols = new DecimalFormatSymbols(currentLocale);
        localDecimalSeparator = localDecimalFormatSymbols.getDecimalSeparator();
        localGroupingSeparator = localDecimalFormatSymbols.getGroupingSeparator();
        acceptedDigits = numbers + localDecimalSeparator;
    }

}
