package com.chaitanya.mathquiz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    int[] buttons = {R.id.zero,R.id.one,R.id.two,R.id.three,R.id.four,R.id.five,R.id.six,R.id.seven,R.id.eigth,R.id.nine};
    Button delete, enter;
    ImageButton home;
    TextView answer,operation,firstNumber,secondNumber,questionTitle,checkAnswer,quizTitle;

    int correctAnswer = 0;
    int correctAnswers = 0;
    int wrongAnswers = 0;

    Quiz quiz = null;
    Random rnd = new Random();
    private Runnable repeat;

    RetainFragment retainFragment;


    @Override
    protected void onPause() {
        super.onPause();
        retainFragment.setData(quiz);
        timer.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        FragmentManager fm = getSupportFragmentManager();
        retainFragment = (RetainFragment) fm.findFragmentByTag("data");

        if(savedInstanceState != null){
            quiz = retainFragment.getData();
            System.out.println("Got quiz data");
            System.out.println(quiz.getOperation());
        }else{
            System.out.println("In else");
            Intent quizIntent = getIntent();
            //0 - Addition
            //1 - Subtraction
            //2 - Multiplication
            int quizType = quizIntent.getIntExtra("operation",0);
            quizTitle =(TextView) findViewById(R.id.quizType);

            switch (quizType){
                case 0: quiz = new Quiz('+');
                    quizTitle.setText("Addition");
                    break;
                case 1: quiz = new Quiz('-');
                    quizTitle.setText("Subtraction");
                    break;
                case 2: quiz = new Quiz('X');
                    quizTitle.setText("Multiplication");
                    break;
            }
            retainFragment = new RetainFragment();
            getSupportFragmentManager().beginTransaction().add(retainFragment,"data").commit();
            retainFragment.setData(quiz);
        }
        operation = (TextView) findViewById(R.id.operation);

        operation.setText(Character.toString(quiz.getOperation()));
        answer = (TextView) findViewById(R.id.answer);
        firstNumber = (TextView) findViewById(R.id.firstNumber);
        secondNumber = (TextView) findViewById(R.id.secondNumber);
        questionTitle = (TextView) findViewById(R.id.questionNumber);
        checkAnswer = (TextView) findViewById(R.id.checkAnswer);

        for(int i = 0; i < buttons.length; i++){
            final int buttonValue = i;
            Button num = (Button) findViewById(buttons[i]);
            num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enterDigit(buttonValue);
                }
            });
        }

        home = (ImageButton) findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                AlertDialog.Builder builder =  new AlertDialog.Builder(QuizActivity.this);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent mainMenu = new Intent(QuizActivity.this, Menu.class);
                                startActivity(mainMenu);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                timer.start();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDigit();
            }
        });

        enter = (Button) findViewById(R.id.enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quiz.checkForAnswer(Integer.parseInt(answer.getText().toString()))){
                    quiz.incCorrect();
                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                }else{
                    quiz.incWrong();
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                timer.cancel();
                newQuestion();
            }
        });
        newQuestion();
    }

    CountDownTimer timer = new CountDownTimer(5000,1000){

        @Override
        public void onTick(long millisUntilFinished) {
            System.out.println(millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            newQuestion();
        }
    };

    private void newQuestion() {
        quiz.incQuestionNumber();
        if(quiz.getQuestionNumber() <= 10) {
            //h.removeCallbacks(repeat);
            timer.start();
            questionTitle.setText("Question No. " + quiz.getQuestionNumber() + " of 10");
            firstNumber.setText(Integer.toString(quiz.generateFirstNumber()));
            secondNumber.setText(Integer.toString(quiz.generateSecondNumber()));
            correctAnswer = quiz.getCorrectAnswer();
            answer.setText("0");
        }else{
            AlertDialog.Builder builder =  new AlertDialog.Builder(QuizActivity.this);
            builder.setMessage("Correct Answers : " + Integer.toString(quiz.getNumCorrect()))
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent mainMenu = new Intent(QuizActivity.this, Menu.class);
                            startActivity(mainMenu);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void deleteDigit() {
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

    private void enterDigit(int num) {
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
        if(quiz.checkForAnswer(Integer.parseInt(answer.getText().toString()))){
            quiz.incCorrect();
            timer.cancel();
            Toast.makeText(QuizActivity.this, "Correct",Toast.LENGTH_SHORT).show();
            newQuestion();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            timer.cancel();
            AlertDialog.Builder builder =  new AlertDialog.Builder(QuizActivity.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent mainMenu = new Intent(QuizActivity.this, Menu.class);
                            startActivity(mainMenu);
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            timer.start();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}