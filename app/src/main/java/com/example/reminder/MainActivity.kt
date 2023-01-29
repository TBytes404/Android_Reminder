package com.example.reminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.reminder.ui.NotesViewModel
import com.example.reminder.ui.componets.NotesScreen

class MainActivity : ComponentActivity() {
    private val notesViewModel: NotesViewModel by viewModels { NotesViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesScreen(notesViewModel)
        }
    }
}

