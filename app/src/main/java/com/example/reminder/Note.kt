package com.example.reminder

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class Note(
    var note: String,
    val time: LocalDateTime = LocalDateTime.now(),
    val id: String = UUID.randomUUID().toString()
) {
    fun formattedTime(pattern: String = "d MMM, yyyy    hh:mm a"): String {
        return time.format(DateTimeFormatter.ofPattern(pattern))
    }
}