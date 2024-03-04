package com.example.mycalculatorv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resTxtView, solTxtView;
    MaterialButton btnC, btnOpenBracket, btnCloseBracket;
    MaterialButton btnDiv, btnMul,buttonPlus,buttonMinus,buttonEquals;
    MaterialButton button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9;
    MaterialButton buttonAC, buttonDot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resTxtView = findViewById(R.id.result_tv);
        solTxtView = findViewById(R.id.solution_tv);

        assignId(btnC,R.id.button_c);
        assignId(btnOpenBracket,R.id.button_open_bracket);
        assignId(btnCloseBracket,R.id.button_close_bracket);
        assignId(btnDiv,R.id.button_divide);
        assignId(btnMul,R.id.button_multiply);
        assignId(buttonPlus,R.id.button_plus);
        assignId(buttonMinus,R.id.button_minus);
        assignId(buttonEquals,R.id.button_equals);
        assignId(button_0,R.id.button_0);
        assignId(button_1,R.id.button_1);
        assignId(button_2,R.id.button_2);
        assignId(button_3,R.id.button_3);
        assignId(button_4,R.id.button_4);
        assignId(button_5,R.id.button_5);
        assignId(button_6,R.id.button_6);
        assignId(button_7,R.id.button_7);
        assignId(button_8,R.id.button_8);
        assignId(button_9,R.id.button_9);
        assignId(buttonAC,R.id.button_ac);
        assignId(buttonDot,R.id.button_dot);
    }

    void assignId(MaterialButton btn,int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String btnTxt = button.getText().toString();
        String calcData = solTxtView.getText().toString();

        if (btnTxt.equals("AC")) {
            solTxtView.setText("");
            resTxtView.setText("0");
            return;
        }

        if (btnTxt.equals("=")) {
            solTxtView.setText(resTxtView.getText());
            return;
        }

        try {
            if (btnTxt.equals("C")) {
                if (calcData.equals("") || calcData.equals("0")) {
                    resTxtView.setText("0");
                    return;
                }
                calcData = calcData.substring(0, calcData.length() - 1);
            } else {
                calcData = calcData + btnTxt;
            }
        } catch(JavaScriptException e) {
            resTxtView.setText("0");
            return;
        }

        solTxtView.setText(calcData);
        String result = getResult(calcData);

        if(!result.equals("Error happened")) {
            resTxtView.setText(result);
        }
    }

    String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String result = context.evaluateString(scriptable,data,"Javascript",1,null).toString();

            if(result.endsWith(".0")) {
                result = result.replace(".0","");
            }

            return result;
        } catch (Exception e) {
            return "Error happened";
        }
    }
}