package com.example.reminder.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY date_time DESC")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id in (:ids)")
    fun getById(vararg ids: Int): List<Note>

//    @Query("SELECT * FROM notes WHERE note LIKE '%:search%' ORDER BY date_time DESC")
//    suspend fun search(search: String): List<Note>
//
//    @Query("SELECT * FROM notes WHERE data_time > :at ORDER BY date_time DESC")
//    suspend fun findAfter(at: LocalDateTime): List<Note>
//
//    @Query("SELECT * FROM notes WHERE data_time < :at ORDER BY date_time DESC")
//    suspend fun findBefore(at: LocalDateTime): List<Note>
//
//    @Query("SELECT * FROM notes WHERE data_time BETWEEN :from AND :at ORDER BY date_time DESC")
//    suspend fun find(from: LocalDateTime, at: LocalDateTime): List<Note>

    @Insert
    suspend fun create(vararg notes: Note)

//    @Delete
//    suspend fun delete(vararg notes: Note)

    @Query("DELETE FROM notes WHERE id IN (:ids)")
    suspend fun deleteById(vararg ids: Int)

    @Update
    suspend fun update(vararg notes: Note)
}