package com.example.dvctriptracking.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object AddTrip : Screen("add_trip")
    object TripDetails : Screen("trip_details/{tripId}") {
        fun createRoute(tripId: Long) = "trip_details/$tripId"
    }
}
