package com.github.noreply.a34734577j_davilausers.show_me_the_answer;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public boolean isShowingAnswers = true;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

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
        // Cycles to next card
        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCardDisplayedIndex++;

                if (currentCardDisplayedIndex > allFlashcards.size() -1){
                currentCardDisplayedIndex = 0;
                }

                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get
                        (currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get
                        (currentCardDisplayedIndex).getAnswer());
            }
        });

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
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
        // Resets the answers by clocking on the background
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
        // Interacting with the icon turns the answers visible or invisible
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
                MainActivity.this.startActivityForResult(intent, 100);
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
            Snackbar.LENGTH_LONG)

            .show();

            }
        }
}

