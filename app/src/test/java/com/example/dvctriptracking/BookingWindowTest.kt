package com.example.dvctriptracking

import com.example.dvctriptracking.data.Trip
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class BookingWindowTest {

    @Test
    fun testBookingWindowCalculations() {
        val checkInDate = LocalDate.of(2025, 12, 25)
        val trip = Trip(
            resortName = "Beach Club Villas",
            checkInDate = checkInDate,
            durationDays = 7
        )

        // 11-month window
        assertEquals(LocalDate.of(2025, 1, 25), trip.bookingWindow11Month)

        // 7-month window
        assertEquals(LocalDate.of(2025, 5, 25), trip.bookingWindow7Month)

        // 60-day ADR window
        assertEquals(LocalDate.of(2025, 10, 26), trip.bookingWindow60Day)
    }

    @Test
    fun testCheckOutDate() {
        val checkInDate = LocalDate.of(2025, 12, 25)
        val trip = Trip(
            resortName = "Beach Club Villas",
            checkInDate = checkInDate,
            durationDays = 5
        )

        assertEquals(LocalDate.of(2025, 12, 30), trip.checkOutDate)
    }
}
