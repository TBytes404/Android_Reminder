package com.example.reminder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel


class NotesViewModel : ViewModel() {
    private val _notes = getNotesList().toMutableStateList()
    val notes: List<Note>
        get() = _notes

    private val _account = Account()

    var accountName by mutableStateOf(_account.name)

    fun updateAccountName(newValue: String?) {
        accountName = newValue?.trim()
        _account.name = accountName
    }

    fun createNote(note: Note) {
        _notes.add(note)
    }
}

private fun getNotesList() = List(10) { Note("I am Note #$it") }