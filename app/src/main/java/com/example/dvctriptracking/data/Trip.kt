package com.example.dvctriptracking.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "trips")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val resortName: String,
    val checkInDate: LocalDate,
    val durationDays: Int,
    val notes: String = ""
) {
    val checkOutDate: LocalDate
        get() = checkInDate.plusDays(durationDays.toLong())

    val bookingWindow11Month: LocalDate
        get() = checkInDate.minusMonths(11)

    val bookingWindow7Month: LocalDate
        get() = checkInDate.minusMonths(7)

    val bookingWindow60Day: LocalDate
        get() = checkInDate.minusDays(60)
}
