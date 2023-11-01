package com.example.note_kotlin

import androidx.room.*

@Dao
interface NotesDao {
    @Query("SELECT * FROM Notes")
    fun queryScoreList(): List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScore(notes: Notes?)
    @Update
    fun update(notes: Notes?)
    @Delete
    fun deleteWords(vararg item: Notes)
}