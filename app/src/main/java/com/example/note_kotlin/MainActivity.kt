package com.example.note_kotlin

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.note_kotlin.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Comment


class MainActivity : AppCompatActivity() ,INoteListener{
    var binding: ActivityMainBinding? = null
    var  nodaAdapter : NoteAdapter? = null;
    var notesDao: NotesDao? = null
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        notesDao = MainApplication.instance?.scoreDataBase?.scoreDao()
        nodaAdapter = NoteAdapter(this);
        database = Firebase.database.reference
        val manager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        binding?.rlNote?.layoutManager = manager;
        binding?.rlNote?.addItemDecoration(MyItemDecoration(10)) ;
        binding?.rlNote?.adapter = nodaAdapter;

        binding?.add?.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNoteActivity::class.java))
        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var list:List<Notes>? = notesDao?.queryScoreList();
                nodaAdapter!!.setNewData(list as MutableList<Notes>?)
                nodaAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                var list:List<Notes>? = notesDao?.queryScoreList();
                nodaAdapter!!.setNewData(list as MutableList<Notes>?)
                nodaAdapter!!.notifyDataSetChanged()
            }
        })
        binding?.user?.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("hint")
                setMessage("login out?")
                setPositiveButton("ok") { dialog, _ ->
                    dialog.dismiss()
                    try {
                        //正常退出
                        Process.killProcess(Process.myPid())
                        System.exit(0)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                setNegativeButton("cancel") { dialog, _ ->
                    //点击了取消按钮
                    dialog.dismiss()
                }
                create()
                show()
            }
        }


        database.addChildEventListener(childEventListener)

    }

    val childEventListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
        }

        override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onCancelled(databaseError: DatabaseError) {
            Toast.makeText(
                applicationContext,
                "Failed to load comments.",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }


    fun addNotes(view: View?) {
        startActivity(Intent(this@MainActivity, AddNoteActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        var list:List<Notes>? = notesDao?.queryScoreList();
        nodaAdapter!!.setNewData(list as MutableList<Notes>?)
        nodaAdapter!!.notifyDataSetChanged()
    }
    override fun onNoteClick(notes: Notes?) {
        var intent = Intent(this@MainActivity, AddNoteActivity::class.java);
        MainApplication.instance?.note = notes
        startActivity(intent)

    }
}