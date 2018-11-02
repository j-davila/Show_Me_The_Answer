package com.github.noreply.a34734577j_davilausers.show_me_the_answer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        // Closes add card activity
        findViewById(R.id.cancel_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Records user input and passes it onto MainActivity to create a new card
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("question_string", ((EditText) findViewById(
                        R.id.question_text)).getText().toString());
                data.putExtra("answer_string", ((EditText) findViewById(
                        R.id.answer_1)).getText().toString());
                data.putExtra("answer2_string", ((EditText) findViewById(
                        R.id.answer_2)).getText().toString());
                data.putExtra("answer3_string", ((EditText) findViewById(
                        R.id.answer_3)).getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
        // Shows the current card question and answers. Allows them to be edited.
        String question_text = getIntent().getStringExtra("question_string");
        String answer_text = getIntent().getStringExtra("answer_string");
        String answer2_text = getIntent().getStringExtra("answer2_string");
        String answer3_text = getIntent().getStringExtra("answer3_string");
        ((EditText) findViewById(R.id.question_text)).setText(question_text,
                TextView.BufferType.EDITABLE);
        ((EditText) findViewById(R.id.answer_1)).setText(answer_text,
                TextView.BufferType.EDITABLE);
        ((EditText) findViewById(R.id.answer_2)).setText(answer2_text,
                TextView.BufferType.EDITABLE);
        ((EditText) findViewById(R.id.answer_3)).setText(answer3_text,
                TextView.BufferType.EDITABLE);
    }
}
