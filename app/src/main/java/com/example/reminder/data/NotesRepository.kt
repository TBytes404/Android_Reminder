package com.example.reminder.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotesRepository {

    fun getAllNotes(): Flow<List<Note>> = flow { emit(NotesDataProvider.notes) }

/*
    fun getNotesOn(date: LocalDate): Flow<List<Note>> = flow {
        val notesOnDay = NotesDataProvider.notes.filter { it.dateTime.dayOfYear.equals(date) }
        emit(notesOnDay)
    }

    fun getNote(id: String): Note? = NotesDataProvider.notes.firstOrNull { it.id == id }

    private fun getIndex(id: String): Int? {
        for ((idx, note) in notes.withIndex())
            if (note.id == id)
                return idx
        return null
    }

    fun find(toFind: String): NoteInventory {
        val matches = NoteInventory()
        for (note in notes)
            if (note.note.contains(toFind))
                matches.add(note)
        return matches
    }

    fun find(atTime: LocalDateTime): NoteInventory {
        val matches = NoteInventory()
        for (note in notes)
            if (note.time == atTime)
                matches.add(note)
        return matches
    }

    fun find(fromTime: LocalDateTime, tillTime: LocalDateTime): NoteInventory {
        val matches = NoteInventory()
        for (note in notes)
            if (note.time > fromTime && note.time < tillTime)
                matches.add(note)
        return matches
    }

    fun remove(id: String): Boolean {
        return try {
            notes.removeIf { n -> n.id == id }
        } catch (e: Exception) {
            return false
        }
    }

    fun update(id: String, changeWith: Note): Boolean {
        val idx = getIndex(id) ?: return false
        notes[idx] = changeWith
        return true
    }
*/
}