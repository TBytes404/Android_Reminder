package com.example.reminder.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.reminder.data.AccountRepository

class MainActivity : ComponentActivity() {
    private lateinit var accountRepository: AccountRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accountRepository = AccountRepository(context = applicationContext)

        setContent {
            NotesScreen(notesViewModel = NotesViewModel(accountRepository))
        }
    }
}

