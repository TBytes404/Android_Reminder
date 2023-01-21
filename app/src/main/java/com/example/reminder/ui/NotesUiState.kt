package com.example.reminder.ui

import com.example.reminder.data.Note

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val accountName: String? = null,
    val prefersDarkTheme: Boolean = false
)
