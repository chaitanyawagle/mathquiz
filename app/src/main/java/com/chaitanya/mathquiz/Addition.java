package com.chaitanya.mathquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class Addition extends AppCompatActivity {

    private TextView firstNum;
    private TextView secondNum;
    private TextView answer;
    private TextView checkAnswer;
    private TextView questionNumber;
    private final Button numPad[] = new Button[12];
    private Random rnd = new Random();
    private int correctAnswer;
    private int questionCounter = 0;
    private int correctAnswerCount = 0;
    private int wrongAnswerCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstNum = (TextView) findViewById(R.id.firstNumber);
        secondNum = (TextView) findViewById(R.id.secondNumber);
        answer = (TextView) findViewById(R.id.answer);
        checkAnswer = (TextView) findViewById(R.id.checkAnswer);
        questionNumber = (TextView) findViewById(R.id.questionNumber);
        numPad[0] = (Button) findViewById(R.id.zero);
        numPad[1] = (Button) findViewById(R.id.one);
        numPad[2] = (Button) findViewById(R.id.two);
        numPad[3] = (Button) findViewById(R.id.three);
        numPad[4] = (Button) findViewById(R.id.four);
        numPad[5] = (Button) findViewById(R.id.five);
        numPad[6] = (Button) findViewById(R.id.six);
        numPad[7] = (Button) findViewById(R.id.seven);
        numPad[8] = (Button) findViewById(R.id.eigth);
        numPad[9] = (Button) findViewById(R.id.nine);
        numPad[10] = (Button) findViewById(R.id.enter);
        numPad[11] = (Button) findViewById(R.id.back);

        newQuestion();

        numPad[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnswer(0);
            }
        });

        numPad[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnswer(1);
            }
        });

        numPad[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnswer(2);
            }
        });

        numPad[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnswer(3);
            }
        });

        numPad[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnswer(4);
            }
        });

        numPad[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnswer(5);
            }
        });

        numPad[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnswer(6);
            }
        });

        numPad[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnswer(7);
            }
        });

        numPad[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnswer(8);
            }
        });

        numPad[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnswer(9);
            }
        });

//        for(int i = 0; i < 10; i++){
//            final int ii = i;
//            numPad[ii].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    enterAnswer(ii);
//                }
//            });
//        }

        numPad[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForAnswer();
                newQuestion();
            }
        });

        numPad[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    public void newQuestion(){
        questionCounter++;
        questionNumber.setText("Question No. " + questionCounter);
            if(questionCounter <= 10) {
                firstNum.setText(Integer.toString(rnd.nextInt(9)));
                secondNum.setText(Integer.toString(rnd.nextInt(9)));
                correctAnswer = Integer.parseInt(firstNum.getText().toString()) + Integer.parseInt(secondNum.getText().toString());
                answer.setText("0");
            }else{
                try{
                    Intent resultIntent = new Intent(Addition.this,  Class.forName("com.chaitanya.mathquiz.Result"));
                    resultIntent.putExtra("correctAnswer",correctAnswerCount);
                    startActivity(resultIntent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }

    public void checkForAnswer(){
        if(Integer.parseInt(answer.getText().toString()) == correctAnswer){
            checkAnswer.setText("Correct!");
            correctAnswerCount++;
        }else{
            checkAnswer.setText("Wrong");
            wrongAnswerCount++;
        }
    }

    public void enterAnswer(int num){
        int existingDigit = 0;
        try{
            existingDigit = Integer.parseInt(answer.getText().toString());
        }catch (NumberFormatException e){
            System.out.println("Exception");
        }
        if(existingDigit == 0){
            answer.setText(Integer.toString(num));
        }else if(existingDigit < 10){
            answer.setText(Integer.toString(10*existingDigit + num));
        }
    }

    public void delete(){
        int existingDigit = 0;
        try{
            existingDigit = Integer.parseInt(answer.getText().toString());
        }catch (NumberFormatException e){
            System.out.println("Exception");
        }
        if(existingDigit != 0){
            answer.setText(Integer.toString(existingDigit/10));
        }
    }
}
