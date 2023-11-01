package com.example.note_kotlin

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class FireData(val title: String? = null, val time: String? = null,val des: String? = null) {
}