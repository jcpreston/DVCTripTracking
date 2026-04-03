package com.example.dvctriptracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dvctriptracking.ui.addtrip.AddTripScreen
import com.example.dvctriptracking.ui.addtrip.AddTripViewModel
import com.example.dvctriptracking.ui.addtrip.AddTripViewModelFactory
import com.example.dvctriptracking.ui.dashboard.DashboardScreen
import com.example.dvctriptracking.ui.dashboard.DashboardViewModel
import com.example.dvctriptracking.ui.dashboard.DashboardViewModelFactory
import com.example.dvctriptracking.ui.details.TripDetailsScreen
import com.example.dvctriptracking.ui.details.TripDetailsViewModel
import com.example.dvctriptracking.ui.details.TripDetailsViewModelFactory
import com.example.dvctriptracking.ui.navigation.Screen
import com.example.dvctriptracking.ui.theme.DVCTripTrackingTheme
import com.example.dvctriptracking.ui.pasttrips.PastTripsScreen
import com.example.dvctriptracking.ui.pasttrips.PastTripsViewModel
import com.example.dvctriptracking.ui.pasttrips.PastTripsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val app = application as DVCApplication
        val repository = app.repository
        
        setContent {
            DVCTripTrackingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(navController = navController, repository = repository)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    repository: com.example.dvctriptracking.data.TripRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            val viewModel: DashboardViewModel = viewModel(
                factory = DashboardViewModelFactory(repository)
            )
            DashboardScreen(
                viewModel = viewModel,
                onAddTripClick = { 
                    navController.navigate(Screen.AddTrip.route)
                },
                onTripClick = { tripId ->
                    navController.navigate(Screen.TripDetails.createRoute(tripId))
                },
                onPastTripsClick = {
                    navController.navigate(Screen.PastTrips.route)
                }
            )
        }
        
        composable(Screen.AddTrip.route) {
            val viewModel: AddTripViewModel = viewModel(
                factory = AddTripViewModelFactory(repository)
            )
            AddTripScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.PastTrips.route) {
            val viewModel: PastTripsViewModel = viewModel(
                factory = PastTripsViewModelFactory(repository)
            )
            PastTripsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onTripClick = { tripId ->
                    navController.navigate(Screen.TripDetails.createRoute(tripId))
                }
            )
        }
        
        composable(
            route = Screen.TripDetails.route,
            arguments = listOf(navArgument("tripId") { type = NavType.LongType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getLong("tripId") ?: return@composable
            val viewModel: TripDetailsViewModel = viewModel(
                factory = TripDetailsViewModelFactory(repository, tripId)
            )
            TripDetailsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
