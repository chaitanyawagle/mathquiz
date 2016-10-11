package com.chaitanya.mathquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    int[] buttons = {R.id.zero,R.id.one,R.id.two,R.id.three,R.id.four,R.id.five,R.id.six,R.id.seven,R.id.eigth,R.id.nine};
    Button delete, enter;
    TextView answer,operation,firstNumber,secondNumber,questionTitle,quizTitle,timeLeft;

    int correctAnswer = 0;

    Quiz quiz;

    RetainFragment retainFragment;
    CountDownTimer timer,tempTimer;


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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if(getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fm = getSupportFragmentManager();
        retainFragment = (RetainFragment) fm.findFragmentByTag("data");

        if(savedInstanceState != null){
            quiz = retainFragment.getData();
        }else{
            System.out.println("In else");
            Intent quizIntent = getIntent();
            //0 - addition
            //1 - subtraction
            //2 - multiplication
            int quizType = quizIntent.getIntExtra("operation",0);

            switch (quizType){
                case 0: quiz = new Quiz('+');
                    break;
                case 1: quiz = new Quiz('-');
                    break;
                case 2: quiz = new Quiz('X');
                    break;
            }
            retainFragment = new RetainFragment();
            getSupportFragmentManager().beginTransaction().add(retainFragment,"data").commit();
            retainFragment.setData(quiz);
        }

        quizTitle =(TextView) findViewById(R.id.quizType);
        switch (quiz.getOperation()){
            case '+': quizTitle.setText(R.string.Addition);
                break;
            case '-': quizTitle.setText(R.string.Subtraction);
                break;
            case 'X': quizTitle.setText(R.string.Mutliplication);
                break;
        }

        operation = (TextView) findViewById(R.id.operation);

        operation.setText(Character.toString(quiz.getOperation()));
        answer = (TextView) findViewById(R.id.answer);
        firstNumber = (TextView) findViewById(R.id.firstNumber);
        secondNumber = (TextView) findViewById(R.id.secondNumber);
        questionTitle = (TextView) findViewById(R.id.questionNumber);
        timeLeft = (TextView) findViewById(R.id.timeLeft);

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
                final Toast toast;
                if(answer.getText().toString().equals(""))
                    toast = Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT);
                else
                    toast = Toast.makeText(QuizActivity.this, quiz.checkForAnswer(Integer.parseInt(answer.getText().toString()))?"Correct":"Wrong", Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 500);
                timer.cancel();
                newQuestion();
            }
        });

        timer = new CountDownTimer(5000,500){

            @Override
            public void onTick(long millisUntilFinished) {
                quiz.setQuestionTimeRemaining(millisUntilFinished);
                setTimer(quiz.getQuestionTimeRemaining());
            }

            @Override
            public void onFinish() {
                newQuestion();
            }
        };

        if(savedInstanceState == null){
            newQuestion();
        }else{
            questionTitle.setText("Question No." + quiz.getQuestionNumber() + " of 10");
            firstNumber.setText(Integer.toString(quiz.getFirstNumber()));
            secondNumber.setText(Integer.toString(quiz.getSecondNumber()));
            tempTimer = new CountDownTimer(quiz.getQuestionTimeRemaining(),500){
                @Override
                public void onTick(long millisUntilFinished) {
                    quiz.setQuestionTimeRemaining(millisUntilFinished);
                    setTimer(quiz.getQuestionTimeRemaining());
                }

                @Override
                public void onFinish() {
                    newQuestion();
                }
            };
            tempTimer.start();
        }
    }

    public void setTimer(long timeRemains){
        timeLeft.setText("Time left: 0" + Long.toString(timeRemains/1000 + 1));
    }

    private void newQuestion() {
        timeLeft.setText(Integer.toString(5));
        quiz.incQuestionNumber();
        if(quiz.getQuestionNumber() <= 10) {
            timer.start();
            questionTitle.setText("Question No. " + quiz.getQuestionNumber() + " of 10");
            firstNumber.setText(Integer.toString(quiz.generateFirstNumber()));
            secondNumber.setText(Integer.toString(quiz.generateSecondNumber()));
            correctAnswer = quiz.getCorrectAnswer();
            answer.setText("");
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
        String existingDigit = answer.getText().toString();
        if(existingDigit.equals("")){
            answer.setText(Integer.toString(num));
        }
        else{
            if (Integer.parseInt(existingDigit) == 0) {
                answer.setText(Integer.toString(num));
            } else if (Integer.parseInt(existingDigit) < 10) {
                answer.setText(Integer.toString(10 * Integer.parseInt(existingDigit) + num));
            }
        }
        if (quiz.checkForAnswer(Integer.parseInt(answer.getText().toString()))) {
            quiz.incCorrect();
            timer.cancel();
            final Toast toast  = Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            },500);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    newQuestion();
                }
            },500);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
//        timer = new CountDownTimer(quiz.getQuestionTimeRemaining(),500) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                quiz.setQuestionTimeRemaining(millisUntilFinished);
//                timeLeft.setText("Time left: 0" + Long.toString(millisUntilFinished/1000 + 1));
//            }
//
//            @Override
//            public void onFinish() {
//                newQuestion();
//            }
//        };
        tempTimer = new CountDownTimer(quiz.getQuestionTimeRemaining(),500) {
            @Override
            public void onTick(long millisUntilFinished) {
                quiz.setQuestionTimeRemaining(millisUntilFinished);
                setTimer(quiz.getQuestionTimeRemaining());
            }

            @Override
            public void onFinish() {
                newQuestion();
            }
        };
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
                            tempTimer.start();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
    }
}