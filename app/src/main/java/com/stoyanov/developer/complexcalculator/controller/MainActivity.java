package com.stoyanov.developer.complexcalculator.controller;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stoyanov.developer.complexcalculator.R;

import org.nfunk.jep.JEP;
import org.nfunk.jep.type.Complex;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int BACKGROUND_DURATION_MILLIS = 700;
    private EditText input;
    private TextView textViewResult;
    private JEP parser;
    private int colorPrimary;
    private int colorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colorPrimary = getResources().getColor(R.color.colorPrimary);
        colorAccent = getResources().getColor(R.color.colorAccent);
        input = (EditText) findViewById(R.id.input);
        textViewResult = (TextView) findViewById(R.id.result);
        parser = new JEP();
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
            }
        });
    }
    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void setNormalBackgroud() {
        findViewById(R.id.input).setBackgroundResource(R.color.colorPrimary);
        findViewById(R.id.result).setBackgroundResource(R.color.colorPrimary);
    }

    public void onClickButtonOperation(View view) {
        setNormalBackgroud();
        int i = view.getId();
        if (i == R.id.addition) {
            addCharacters("+");

        } else if (i == R.id.subtraction) {
            addCharacters("-");

        } else if (i == R.id.division) {
            addCharacters("/");

        } else if (i == R.id.equally) {
            String expression = input.getText().toString();
            setResult(calculate(expression));
        } else if (i == R.id.multiplication) {
            addCharacters("*");

        } else if (i == R.id.bracket_left) {
            addCharacters("(");

        } else if (i == R.id.bracket_right) {
            addCharacters(")");

        } else if (i == R.id.complex_expression) {
            addCharacters("(a+b*i)");

        } else if (i == R.id.dot) {
            addCharacters(".");

        } else if (i == R.id.power) {
            addCharacters("^ ");

        }
    }

    private String calculate(String expression) {
        parser.addComplex();
        String result = null;
        try {
            parser.parseExpression(expression);
            Complex complex = parser.getComplexValue();
            result = complex.re() +"+"+ complex.im() +"*i";
        } catch(Exception e) {
            e.printStackTrace();
            textViewResult.setText("ERROR");
            changeColor(input, colorPrimary, colorAccent);
            changeColor(textViewResult, colorPrimary, colorAccent);
        }
        return result;
    }

    private void changeColor(View view, int resColorFrom, int resColorTo) {
        ColorDrawable[] color = {new ColorDrawable(resColorFrom), new ColorDrawable(resColorTo)};
        TransitionDrawable trans = new TransitionDrawable(color);
        view.setBackgroundDrawable(trans);
        trans.startTransition(BACKGROUND_DURATION_MILLIS);
    }

    private void setResult(String result) {
        if (result != null) {
            textViewResult.setText(result);
        }
    }

    public void onClickButtonNumber(View view) {
        int i = view.getId();
        if (i == R.id.number0) {
            addCharacters("0");

        } else if (i == R.id.number1) {
            addCharacters("1");

        } else if (i == R.id.number2) {
            addCharacters("2");

        } else if (i == R.id.number3) {
            addCharacters("3");

        } else if (i == R.id.number4) {
            addCharacters("4");

        } else if (i == R.id.number5) {
            addCharacters("5");

        } else if (i == R.id.number6) {
            addCharacters("6");

        } else if (i == R.id.number7) {
            addCharacters("7");

        } else if (i == R.id.number8) {
            addCharacters("8");

        } else if (i == R.id.number9) {
            addCharacters("9");

        }
        setNormalBackgroud();
    }

    private void addCharacters(String newCharacters) {
        int start = input.getSelectionStart();
        input.getText().insert(start, newCharacters);
    }

    public void onClickButtonAdditional(View view) {
        int i = view.getId();
        if (i == R.id.clear) {
            textViewResult.setText("");
            input.setText("");
            setNormalBackgroud();
        } else if (i == R.id.delete) {
            deleteTextInput();
            setNormalBackgroud();
        } else if (i == R.id.history) {
            Toast.makeText(MainActivity.this, "History", Toast.LENGTH_SHORT).show();
        } else if (i == R.id.about) {
            int[] startingLocation = new int[2];
            view.getLocationOnScreen(startingLocation);
            startingLocation[0] += view.getWidth() / 2;
            AboutActivity.startUserProfileFromLocation(startingLocation, this);
            overridePendingTransition(0, 0);
        }
    }

    private void deleteTextInput() {
        int start = input.getSelectionStart();
        int end = input.getSelectionEnd();
        StringBuffer currentInput = new StringBuffer(input.getText().toString());
        if (start != end) {
            currentInput.replace(start, end, "");
            input.setText(currentInput);
        } else {
            if (start != 0) {
                currentInput.replace(start - 1, start, "");
                input.setText(currentInput);
                input.setSelection(start - 1);
            }
        }
    }
}
