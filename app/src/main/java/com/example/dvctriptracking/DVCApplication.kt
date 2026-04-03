package com.example.dvctriptracking

import android.app.Application
import com.example.dvctriptracking.data.AppDatabase
import com.example.dvctriptracking.data.TripRepository

class DVCApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val repository: TripRepository by lazy { TripRepository(database.tripDao()) }
}
