package com.github.noreply.a34734577j_davilausers.show_me_the_answer;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FlashcardDao {
    @Query("SELECT * FROM flashcard")
    List<Flashcard> getAll();

    @Insert
    void insertAll(Flashcard... flashcards);

    @Delete
    void delete(Flashcard flashcard);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Flashcard flashcard);
}
