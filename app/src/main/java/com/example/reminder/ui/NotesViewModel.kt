package com.example.reminder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.reminder.data.AccountRepository
import com.example.reminder.data.Note
import com.example.reminder.data.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class NotesUiState(
    val notes: List<Note> = emptyList(),
)

data class AccountUiState(
    val accountName: String? = null, val prefersDarkTheme: Boolean = false
)


class NotesViewModel(
    private val accountRepository: AccountRepository,
    private val notesRepository: NotesRepository,
) : ViewModel() {

    val notesUiState: StateFlow<NotesUiState> = notesRepository.allNotes.map { NotesUiState(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), NotesUiState())

    val accountUiState: StateFlow<AccountUiState> =
        accountRepository.account.map { AccountUiState(it.name, it.prefersDarkTheme) }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), AccountUiState()
        )


    fun saveAccountName(name: String?) =
        viewModelScope.launch { accountRepository.saveAccountName(name) }

    fun saveThemePreference(prefersDarkTheme: Boolean) =
        viewModelScope.launch { accountRepository.saveThemePreference(prefersDarkTheme) }


//    val allNotes: LiveData<List<Note>> = notesRepository.allNotes.asLiveData()

    fun createNote(note: Note) = viewModelScope.launch { notesRepository.addNote(note) }

    fun removeNotes(vararg ids: Int) =
        viewModelScope.launch { notesRepository.removeNotes(ids = ids) }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as ReminderApplication)
                NotesViewModel(app.accountRepository, app.notesRepository)
            }
        }
    }
}