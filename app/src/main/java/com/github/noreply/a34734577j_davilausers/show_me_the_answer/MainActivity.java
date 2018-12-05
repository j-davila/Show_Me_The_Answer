package com.github.noreply.a34734577j_davilausers.show_me_the_answer;


import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public boolean isShowingAnswers = true;
    Flashcard cardtoEdit;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;
    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase((getApplicationContext()));
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question))
                    .setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer))
                    .setText(allFlashcards.get(0).getAnswer());
        }
        // Cycles to next random card
        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation leftOutAnim = AnimationUtils.loadAnimation
                        (v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation
                        (v.getContext(), R.anim.right_in);
                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);
                findViewById(R.id.flashcard_question).startAnimation(rightInAnim);

                int randomNumber =  getRandomNumber(0, allFlashcards.size() -1);

                if (randomNumber > allFlashcards.size() -1){
                    randomNumber = 0;
                }

                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get
                        (randomNumber).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get
                        (randomNumber).getAnswer());

            }
        });

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View answerSideView = findViewById(R.id.flashcard_answer);
                View questionSideView = findViewById(R.id.flashcard_question);

                int cx = answerSideView.getWidth() / 2;
                int cy = answerSideView.getHeight() / 2;

                float finalRadius = (float) Math.hypot(cx, cy);

                Animator anim = ViewAnimationUtils.createCircularReveal
                        (answerSideView, cx, cy, 0f, finalRadius);

                questionSideView.setVisibility(View.INVISIBLE);
                answerSideView.setVisibility(View.VISIBLE);

                anim.setDuration(1000);
                anim.start();
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
            }
        });
        // Deletes current card and shows previous card
        findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question))
                        .getText().toString());

                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayedIndex++;

                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get
                        (currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get
                        (currentCardDisplayedIndex).getAnswer());
            }
        });
        // Resets the answers by clicking on the background
        findViewById(R.id.app_background).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor
                        (R.color.background));
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor
                        (R.color.background));
                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor
                        (R.color.background));
            }
        });
        // Interacting with the eye icon turns the answers visible or invisible
        findViewById(R.id.visible_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (isShowingAnswers) {
                    findViewById(R.id.answer1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer3).setVisibility(View.INVISIBLE);
                    ((ImageView) findViewById(R.id.visible_button)).setImageResource(
                            R.drawable.eye_invisible);
                    findViewById(R.id.delete_button).setVisibility(View.VISIBLE);
                    isShowingAnswers = false;

                }

                else {
                    findViewById(R.id.answer1).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer2).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer3).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.visible_button)).setImageResource(
                            R.drawable.visible_eye);
                    findViewById(R.id.delete_button).setVisibility(View.INVISIBLE);
                    isShowingAnswers = true;
                }
            }
        });
        // Interacting with the add icon takes the user to new activity to add another card
        // If user inputs text and creates a new card the information is passed and stored here
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        // Allows user to click on edit button and change question
        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);

                intent.putExtra("question_string", ((TextView) findViewById(
                        R.id.flashcard_question)).getText().toString());
                intent.putExtra("flashcard_string", ((TextView) findViewById(
                        R.id.flashcard_answer)).getText().toString());
                intent.putExtra("answer_string", ((TextView) findViewById(
                        R.id.answer1)).getText().toString());
                intent.putExtra("answer2_string", ((TextView) findViewById(
                        R.id.answer2)).getText().toString());
                intent.putExtra("answer3_string", ((TextView) findViewById(
                        R.id.answer3)).getText().toString());

                cardtoEdit = allFlashcards.get(currentCardDisplayedIndex);

                MainActivity.this.startActivityForResult(intent, 200);
            }
        });
    }
    @Override
    // Matches the request code and returns the user input to create a new card
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {

        if (requestcode == 100 && resultcode == RESULT_OK) {

            String question_string = data.getExtras().getString("question_string");
            String back_answer_string = data.getExtras().getString("back_answer_string");
            String answer_string = data.getExtras().getString("answer_string");
            String answer2_string = data.getExtras().getString("answer2_string");
            String answer3_string = data.getExtras().getString("answer3_string");

            ((TextView) findViewById(R.id.flashcard_question)).setText(question_string);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(back_answer_string);
            ((TextView) findViewById(R.id.answer1)).setText(answer_string);
            ((TextView) findViewById(R.id.answer2)).setText(answer2_string);
            ((TextView) findViewById(R.id.answer3)).setText(answer3_string);

            flashcardDatabase.insertCard(new Flashcard(question_string, back_answer_string));
            allFlashcards = flashcardDatabase.getAllCards();

            Snackbar.make(findViewById(R.id.app_background),"Card created succesfully",
            Snackbar.LENGTH_LONG).show();

            }

        else if (requestcode == 200 && resultcode == RESULT_OK) {

            String question_string = data.getExtras().getString("question_string");
            String back_answer_string = data.getExtras().getString("back_answer_string");
            String answer_string = data.getExtras().getString("answer_string");
            String answer2_string = data.getExtras().getString("answer2_string");
            String answer3_string = data.getExtras().getString("answer3_string");

            ((TextView) findViewById(R.id.flashcard_question)).setText(question_string);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(back_answer_string);
            ((TextView) findViewById(R.id.answer1)).setText(answer_string);
            ((TextView) findViewById(R.id.answer2)).setText(answer2_string);
            ((TextView) findViewById(R.id.answer3)).setText(answer3_string);

            cardtoEdit.setQuestion(question_string);
            cardtoEdit.setAnswer(back_answer_string);

            flashcardDatabase.updateCard(cardtoEdit);
            allFlashcards = flashcardDatabase.getAllCards();

            Snackbar.make(findViewById(R.id.app_background),"Card edited succesfully",
                    Snackbar.LENGTH_LONG).show();
        }
        }
}

