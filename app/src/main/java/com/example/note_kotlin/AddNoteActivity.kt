package com.example.note_kotlin

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.note_kotlin.DateUtil.getCurrentDate
import com.example.note_kotlin.databinding.ActivityAddNoteBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class AddNoteActivity : AppCompatActivity() {
    var binding: ActivityAddNoteBinding? = null
    var notesDao: NotesDao? = null

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
                notesDao = MainApplication.instance?.scoreDataBase?.scoreDao()
        if (MainApplication.instance?.note !=null){
            binding?.etTitle?.setText(MainApplication.instance?.note?.title)
            binding?.etDescription?.setText(MainApplication.instance?.note?.des)
            binding?.btnAdd?.text = "change"
            binding?.delete?.visibility = View.VISIBLE
        }
        database = Firebase.database.reference
        binding?.delete?.setOnClickListener {
            notesDao?.deleteWords(MainApplication.instance?.note!!)
            finish()
        }

    }

    fun save(view: View?) {
        val title = binding!!.etTitle.text.toString()
        val description = binding!!.etDescription.text.toString()
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            Toast.makeText(applicationContext, "please input", Toast.LENGTH_SHORT).show()
            return
        }



        if (MainApplication.instance?.note !=null){
            MainApplication.instance?.note?.title = title
            MainApplication.instance?.note?.des = description
            notesDao!!.update(MainApplication.instance?.note)
            Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val notes = Notes()
        notes.des = description
        notes.title = title
        notes.time = getCurrentDate(DateUtil.FORMAT)
        val time =  getCurrentDate(DateUtil.FORMAT);
        val des = description
        val FireData = FireData(title, time ,des)
        database.child("FireData").setValue(FireData)
        notesDao!!.insertScore(notes)
        Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
        finish()
    }
}