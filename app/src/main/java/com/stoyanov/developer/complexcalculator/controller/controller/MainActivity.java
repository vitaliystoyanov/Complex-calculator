package com.stoyanov.developer.complexcalculator.controller.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stoyanov.developer.complexcalculator.R;

import org.nfunk.jep.JEP;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    private static final String TAG = "dbg";
    private static final int BACKGROUND_DURATION_MILLIS = 700;
    private TextView outputResult;
    private LinearLayout inputLayout;
    private int colorPrimaryResource;
    private int colorAccentResource;
    private EditText inputExpression;
    private JEP parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colorPrimaryResource = getResources().getColor(R.color.colorPrimary);
        colorAccentResource = getResources().getColor(R.color.colorAccent);
        inputExpression = (EditText) findViewById(R.id.input);
        inputExpression.addTextChangedListener(this);
        outputResult = (TextView) findViewById(R.id.output_result);
        inputLayout = (LinearLayout) findViewById(R.id.input_layout);
        parser = new JEP();
        inputExpression.clearFocus();
        inputExpression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
            }
        });
    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void onClickButtonsOperation(View view) {
        int i = view.getId();
        if (i == R.id.addition) {
            addCharacters("+");
        } else if (i == R.id.subtraction) {
            addCharacters("-");
        } else if (i == R.id.division) {
            addCharacters("/");
        } else if (i == R.id.equally) {
            calculateExpression();
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
            addCharacters("^");
        }
    }

    private void calculateExpression() {
        String expression = inputExpression.getText().toString();
        setResult(calculate(expression));
    }

    // TODO Вынести с этого класса.
    private String calculate(String expression) {
        parser.addComplex();
        String result = null;
        try {
            parser.parseExpression(expression);
            Complex complex = parser.getComplexValue();
            result = complex.re() +"+"+ complex.im() +"*i";
        } catch(Exception e) {
            e.printStackTrace();
            outputResult.setText(R.string.errorCalculateExpression);
            changeColorBackground(inputLayout, colorPrimaryResource, colorAccentResource);
        }
        return result;
    }

    private void setResult(String textResult) {
        if (textResult != null) {
            outputResult.setText(textResult);
        }
    }

    public void onClickButtonsNumber(View view) {
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
    }

    private void addCharacters(String characters) {
        int start = inputExpression.getSelectionStart();
        inputExpression.getText().insert(start, characters);
    }

    public void onClickButtonsBar(View view) {
        int i = view.getId();
        if (i == R.id.clear) {
            clear();
        } else if (i == R.id.delete) {
            deleteSelectedTextInput();
        } else if (i == R.id.history) {
            Toast.makeText(MainActivity.this, "History", Toast.LENGTH_SHORT).show();
        } else if (i == R.id.about) {
            startAboutActivity(view);
        }
    }

    private void startAboutActivity(View view) {
        int[] startingLocation = new int[2];
        view.getLocationOnScreen(startingLocation);
        startingLocation[0] += view.getWidth() / 2;
        AboutActivity.startUserProfileFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }

    private void clear() {
        outputResult.setText("");
        inputExpression.setText("");
    }

    private void deleteSelectedTextInput() {
        int start = inputExpression.getSelectionStart();
        int end = inputExpression.getSelectionEnd();
        StringBuffer currentInput = new StringBuffer(inputExpression.getText().toString());
        if (start != end) {
            currentInput.replace(start, end, "");
            inputExpression.setText(currentInput);
        } else {
            if (start != 0) {
                currentInput.replace(start - 1, start, "");
                inputExpression.setText(currentInput);
                inputExpression.setSelection(start - 1);
            }
        }
    }

    public void onClickButtonsDrawerLayout(View view) {
        int id = view.getId();
        if (id == R.id.addition_sqrt) {
            addCharacters("()^1/2");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (getColorBackground(inputLayout) == colorAccentResource) {
            changeColorBackground(inputLayout, colorAccentResource, colorPrimaryResource);
        }
    }

    private void changeColorBackground(View view, int resColorFrom, int resColorTo) {
        ColorDrawable[] color = {new ColorDrawable(resColorFrom), new ColorDrawable(resColorTo)};
        TransitionDrawable trans = new TransitionDrawable(color);
        view.setBackgroundDrawable(trans);
        trans.startTransition(BACKGROUND_DURATION_MILLIS);
    }

    private int getColorBackground(View view) {
        int color = Color.TRANSPARENT;
//        Drawable background = view.getBackground();
//        if (background instanceof ColorDrawable)
//            color = ((ColorDrawable) background).getColor();
//        Log.i(TAG, "getColorBackground: color - " + color);
        return color;
    }
}
