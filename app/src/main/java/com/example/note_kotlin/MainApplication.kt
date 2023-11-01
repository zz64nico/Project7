package com.example.note_kotlin

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.google.firebase.FirebaseApp

class MainApplication : Application() {
    var scoreDataBase: NotesDataBase? = null
        private set
    override fun onCreate() {
        super.onCreate()
        instance = this

        FirebaseApp.initializeApp(this);
        scoreDataBase = databaseBuilder(instance!!, NotesDataBase::class.java, "Notes")
            .addMigrations()
            .allowMainThreadQueries()
            .build()
    }

    var note:Notes? =null

    companion object {
        var instance: MainApplication? = null
            private set
    }
}