package com.example.reminder.data

import androidx.room.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun fromDate(date: LocalDateTime): Long {
        return date.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun toDate(date: Long): LocalDateTime {
        return LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC)
    }
}

@Entity(tableName = "notes")
@TypeConverters(Converters::class)
data class Note(
    @ColumnInfo(name = "note") var note: String,
    @ColumnInfo(name = "date_time") val dateTime: LocalDateTime = LocalDateTime.now(),
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Ignore
    fun formattedTime(pattern: String = "d MMM, yyyy    hh:mm a"): String {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern))
    }
}