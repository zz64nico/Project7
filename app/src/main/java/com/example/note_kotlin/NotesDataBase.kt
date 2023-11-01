package com.example.note_kotlin

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDataBase : RoomDatabase() {
    abstract fun scoreDao(): NotesDao?
}