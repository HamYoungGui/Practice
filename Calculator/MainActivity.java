package com.myProject.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean isFirstInput = true;
    int resultNumber = 0;
    char operator = '+';
    TextView resultText;
    final String CLEAR_INPUT_TEXT = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = findViewById(R.id.result_text);
    }

    public void numButton(String number){
        resultText.setTextColor(0xFF000000);
        if (isFirstInput != true) {
            String result = resultText.getText().toString();
            result = result + number;
            resultText.setText(result);
        } else {
            resultText.setText(number);
            isFirstInput = false;
        }

//        if(isFirstInput){
//            resultText.setTextColor(0xFF000000);
//            resultText.setText(getButton.getText().toString());
//            isFirstInput = false;
//        }else
//            resultText.append(getButon.getText().toString());
    }

    public void buttonClick(View view) {
        Button getButton = findViewById(view.getId());

        switch (view.getId()){
            case R.id.num_0_button: numButton("0"); break;
            case R.id.num_1_button: numButton("1"); break;
            case R.id.num_2_button: numButton("2"); break;
            case R.id.num_3_button: numButton("3"); break;
            case R.id.num_4_button: numButton("4"); break;
            case R.id.num_5_button: numButton("5"); break;
            case R.id.num_6_button: numButton("6"); break;
            case R.id.num_7_button: numButton("7"); break;
            case R.id.num_8_button: numButton("8"); break;
            case R.id.num_9_button: numButton("9"); break;

            case R.id.all_clear_button:
                isFirstInput = true;
                resultNumber = 0;
                operator = '+';
                resultText.setTextColor(0xFF666666);
                resultText.setText(String.valueOf(resultNumber));
                break;

            case R.id.clear_entry_button:
                isFirstInput = true;
                resultText.setText(CLEAR_INPUT_TEXT);
                break;

            case R.id.back_space_button:
                if(resultText.getText().toString().length() > 1){
                    String getResultText = resultText.getText().toString();
                    String subString = getResultText.substring(0, getResultText.length() - 1);
                    resultText.setText(subString);
                }else{
                    resultText.setText(CLEAR_INPUT_TEXT);
                    isFirstInput = true;
                }
                break;

            case R.id.addition_button:
            case R.id.subtraction_button:
            case R.id.multiply_button:
            case R.id.division_button:
            case R.id.decimal_button: {
                int lastNumber = Integer.parseInt(resultText.getText().toString());
                if (operator == '+')
                    resultNumber += lastNumber;
                else if (operator == '-')
                    resultNumber -= lastNumber;
                else if (operator == '*')
                    resultNumber *= lastNumber;
                else if (operator == '/')
                    resultNumber /= lastNumber;

                operator = getButton.getText().toString().charAt(0);
                resultText.setText(String.valueOf(resultNumber));
                isFirstInput = true;
                break;
            }

            case R.id.result_button: {
                int lastNumber = Integer.parseInt(resultText.getText().toString());
                if (operator == '+')
                    resultNumber += lastNumber;
                else if (operator == '-')
                    resultNumber -= lastNumber;
                else if (operator == '*')
                    resultNumber *= lastNumber;
                else if (operator == '/')
                    resultNumber /= lastNumber;

                resultText.setText(String.valueOf(resultNumber));
                isFirstInput = true;
                break;
            }
            default:{
                Toast.makeText(getApplicationContext(),
                         getButton.getText().toString() +"버튼이 클릭 되었습니다.",
                        Toast.LENGTH_LONG).show();
                break;
            }
        }

    }
}