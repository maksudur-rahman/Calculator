package com.example.rotno.bitmcalculator;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

@TargetApi(Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements OnClickListener {

    private TextView mCalculatorDisplay;
    private EditText whereType;
    private static final String DIGITS = "0123456789.";
    private Boolean userIsInTheMiddleOfTypingANumber = false;

    DecimalFormat df = new DecimalFormat("@###########");

    CalculatorBrain mCalculatorBrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mCalculatorBrain = new CalculatorBrain();

        mCalculatorDisplay = (TextView) findViewById(R.id.tv1);
        whereType = (EditText) findViewById(R.id.edt1);

        df.setMinimumFractionDigits(0);
        df.setMinimumIntegerDigits(1);
        df.setMaximumIntegerDigits(8);

        findViewById(R.id.button0).setOnClickListener((OnClickListener) this);
        findViewById(R.id.button1).setOnClickListener((OnClickListener) this);
        findViewById(R.id.button2).setOnClickListener((OnClickListener) this);
        findViewById(R.id.button3).setOnClickListener((OnClickListener) this);
        findViewById(R.id.button4).setOnClickListener((OnClickListener) this);
        findViewById(R.id.button5).setOnClickListener((OnClickListener) this);
        findViewById(R.id.button6).setOnClickListener((OnClickListener) this);
        findViewById(R.id.button7).setOnClickListener((OnClickListener) this);
        findViewById(R.id.button8).setOnClickListener((OnClickListener) this);
        findViewById(R.id.button9).setOnClickListener((OnClickListener) this);

        findViewById(R.id.buttonAdd).setOnClickListener((OnClickListener) this);
        findViewById(R.id.buttonSubtract).setOnClickListener((OnClickListener) this);
        findViewById(R.id.buttonMultiply).setOnClickListener((OnClickListener) this);
        findViewById(R.id.buttonDivide).setOnClickListener((OnClickListener) this);
        findViewById(R.id.buttonEquals).setOnClickListener((OnClickListener) this);
        findViewById(R.id.buttonClear).setOnClickListener((OnClickListener) this);
        findViewById(R.id.buttonDecimalPoint).setOnClickListener((OnClickListener) this);
    }
    @Override
    public void onClick(View v) {

        String buttonPressed = ((Button) v).getText().toString();
        // String digits = "0123456789.";

        if (DIGITS.contains(buttonPressed)) {

            // digit was pressed
            if (userIsInTheMiddleOfTypingANumber) {

//				Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();

                if (buttonPressed.equals(".") && whereType.getText().toString().contains(".")) {
                    // ERROR PREVENTION
                    // Eliminate entering multiple decimals
//					Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                } else {
                    whereType.append(buttonPressed);
//					Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                }

            } else {

                if (buttonPressed.equals(".")) {
                    // ERROR PREVENTION
                    // This will avoid error if only the decimal is hit before an operator, by placing a leading zero before the decimal
                    whereType.setText(0 + buttonPressed);
                } else {
                    whereType.setText(buttonPressed);
                }

                userIsInTheMiddleOfTypingANumber = true;
            }

        } else {
            // operation was pressed
            if (userIsInTheMiddleOfTypingANumber) {

                mCalculatorBrain.setOperand(Double.parseDouble(whereType.getText().toString()));
                userIsInTheMiddleOfTypingANumber = false;
            }

            mCalculatorBrain.performOperation(buttonPressed);
            mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save variables on screen orientation change
        outState.putDouble("OPERAND", mCalculatorBrain.getResult());
        outState.putDouble("MEMORY", mCalculatorBrain.getMemory());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables on screen orientation change
        mCalculatorBrain.setOperand(savedInstanceState.getDouble("OPERAND"));
        mCalculatorBrain.setMemory(savedInstanceState.getDouble("MEMORY"));
        mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));
    }


    public void toSecond(View view) {
        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
    }
}
