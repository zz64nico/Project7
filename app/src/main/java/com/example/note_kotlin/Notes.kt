package com.example.note_kotlin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Notes {
    var title
            : String? = null

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var time
            : String?= null
    var des: String? = null
}