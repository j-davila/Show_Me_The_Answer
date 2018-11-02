package com.github.noreply.a34734577j_davilausers.show_me_the_answer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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
                        R.id.answer_text)).getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
