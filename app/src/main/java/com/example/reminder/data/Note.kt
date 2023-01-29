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
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "note") val note: String,
    @ColumnInfo(name = "date_time") val dateTime: LocalDateTime,
    @ColumnInfo(name = "disabled") var disabled: Boolean = false
) {
    @Ignore
    fun formattedTime(pattern: String = "d MMM, yyyy    hh:mm a"): String {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern))
    }

    @Ignore
    val delay: Long = dateTime.toInstant(ZoneOffset.UTC).epochSecond -
            LocalDateTime.now().toInstant(ZoneOffset.UTC).epochSecond

}