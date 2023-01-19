package com.example.reminder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminder.data.AccountRepository
import com.example.reminder.data.Note
import com.example.reminder.data.NotesRepository
import com.example.reminder.data.ThemePreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class NotesViewModel(
    private val notesRepository: NotesRepository = NotesRepository(),
    private val accountRepository: AccountRepository = AccountRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    init {
        observeNotes()
    }

    private fun observeNotes() {
        viewModelScope.launch {
            notesRepository.getAllNotes().catch {}.collect {
                _uiState.value = NotesUiState(notes = it, accountName = accountRepository.getName(), themePreference = accountRepository.getThemePreference())
            }
        }
    }

    fun setAccountName(name: String?) {
        accountRepository.setName(name)
        _uiState.update { it.copy(accountName = accountRepository.getName()) }
    }

    fun setThemePreference(theme: ThemePreferences) {
        accountRepository.setThemePreference(theme)
        _uiState.update { it.copy(themePreference = accountRepository.getThemePreference()) }
    }

    fun createNote(note: Note) {
        _uiState.update { it.copy(notes = _uiState.value.notes + note) }
    }

    fun deleteNotes(ids: List<String>) {
        _uiState.update { state -> state.copy(notes = _uiState.value.notes.filterNot { it.id in ids }) }
    }
}