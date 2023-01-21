package com.example.reminder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminder.data.AccountRepository
import com.example.reminder.data.Note
import com.example.reminder.data.NotesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException


class NotesViewModel(
    private val accountRepository: AccountRepository,
    private val notesRepository: NotesRepository = NotesRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            accountRepository.account.catch {
                if (it is IOException) {
                    it.printStackTrace()
                    _uiState.value = NotesUiState()
                } else throw it
            }.collect {
                _uiState.value =
                    NotesUiState(accountName = it.name, prefersDarkTheme = it.prefersDarkTheme)
            }

            notesRepository.getAllNotes().catch {
                if (it is IOException) it.printStackTrace()
                else throw it
            }.collect { notes ->
                _uiState.update { it.copy(notes = notes) }
            }
        }
    }


    fun saveAccountName(name: String?) {
        viewModelScope.launch {
            accountRepository.saveAccountName(name)
            accountRepository.accountName.catch {
                if (it is IOException) it.printStackTrace()
                else throw it
            }.collect { name ->
                _uiState.update { it.copy(accountName = name) }
            }
        }
    }

    fun saveThemePreference(prefersDarkTheme: Boolean) {
        viewModelScope.launch {
            accountRepository.saveThemePreference(prefersDarkTheme)
            accountRepository.prefersDarkTheme.catch {
                if (it is IOException) it.printStackTrace()
                else throw it
            }.collect { theme ->
                _uiState.update { it.copy(prefersDarkTheme = theme) }
            }
        }
    }


    fun createNote(note: Note) {
        _uiState.update { it.copy(notes = _uiState.value.notes + note) }
    }

    fun deleteNotes(ids: List<String>) {
        _uiState.update { state -> state.copy(notes = _uiState.value.notes.filterNot { it.id in ids }) }
    }
}