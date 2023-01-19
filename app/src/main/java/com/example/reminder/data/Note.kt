package com.example.reminder.data

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class Note(
    var note: String,
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val id: String = UUID.randomUUID().toString()
) {
    fun formattedTime(pattern: String = "d MMM, yyyy    hh:mm a"): String {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern))
    }
}