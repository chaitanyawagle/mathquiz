package com.chaitanya.mathquiz;

import java.util.Random;

/**
 * Created by 33843 on 10/3/2016.
 */
public class Quiz {

    char operation;
    Random rnd = new Random();
    int firstNumber, secondNumber, numCorrect, numWrong, questionNumber;
    long questionTimeRemaining;

    public long getQuestionTimeRemaining() {
        return questionTimeRemaining;
    }

    public void setQuestionTimeRemaining(long questionTimeRemaining) {
        this.questionTimeRemaining = questionTimeRemaining;
    }

    public Quiz(char operation){
        this.operation = operation;
        this.numCorrect = 0;
        this.numWrong = 0;
        this.questionNumber = 0;
        this.questionTimeRemaining = 5000;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void incQuestionNumber(){
        this.questionNumber++;
    }

    public int generateFirstNumber(){
        if(this.operation == '-'){
            this.firstNumber = rnd.nextInt(9)+1;
        }
        else {
            this.firstNumber = rnd.nextInt(10);
        }
        return this.firstNumber;
    }

    public int generateSecondNumber(){
        if(this.operation == '-')
            this.secondNumber = rnd.nextInt(this.firstNumber);
        else
            this.secondNumber = rnd.nextInt(10);
        return this.secondNumber;
    }

    public int getCorrectAnswer(){
        switch (this.operation){
            case '+': return this.firstNumber + this.secondNumber;
            case '-': return this.firstNumber - this.secondNumber;
            case 'X': return this.firstNumber * this.secondNumber;
            default: return 0;
        }
    }

    public boolean checkForAnswer(int answer){
        return answer==getCorrectAnswer()? true : false;
    }

    public void incCorrect(){
        this.numCorrect++;
    }

    public void incWrong(){
        this.numWrong++;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public void setNumCorrect(int numCorrect) {
        this.numCorrect = numCorrect;
    }

    public int getNumWrong() {
        return numWrong;
    }

    public void setNumWrong(int numWrong) {
        this.numWrong = numWrong;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    public char getOperation() {
        return operation;
    }

    public void setOperation(char operation) {
        this.operation = operation;
    }
}