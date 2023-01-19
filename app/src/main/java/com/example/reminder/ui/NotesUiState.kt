package com.example.reminder.ui

import com.example.reminder.data.Note
import com.example.reminder.data.ThemePreferences

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val accountName: String? = null,
    val themePreference: ThemePreferences = ThemePreferences.AUTO
)
