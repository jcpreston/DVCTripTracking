package com.example.dvctriptracking.ui.addtrip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dvctriptracking.data.Resorts
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTripScreen(
    viewModel: AddTripViewModel,
    onNavigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showResortPicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Trip") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Resort Selection
            ResortSelector(
                selectedResort = viewModel.resortName,
                onClick = { showResortPicker = true }
            )

            // Date Selection
            DateSelector(
                selectedDate = viewModel.checkInDate,
                onClick = { showDatePicker = true }
            )

            // Duration Selection
            OutlinedTextField(
                value = viewModel.durationDays,
                onValueChange = { viewModel.updateDuration(it) },
                label = { Text("Duration (Nights)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                )
            )

            // Notes
            OutlinedTextField(
                value = viewModel.notes,
                onValueChange = { viewModel.updateNotes(it) },
                label = { Text("Notes (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.saveTrip(onSuccess = onNavigateBack)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.resortName.isNotBlank()
            ) {
                Text("Save Trip")
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = viewModel.checkInDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val date = Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        viewModel.updateCheckInDate(date)
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showResortPicker) {
        AlertDialog(
            onDismissRequest = { showResortPicker = false },
            title = { Text("Select Resort") },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Resorts.list.forEach { resort ->
                        ListItem(
                            headlineContent = { Text(resort) },
                            modifier = Modifier.clickable {
                                viewModel.updateResortName(resort)
                                showResortPicker = false
                            }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showResortPicker = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ResortSelector(
    selectedResort: String,
    onClick: () -> Unit
) {
    OutlinedTextField(
        value = selectedResort,
        onValueChange = {},
        label = { Text("Resort") },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        enabled = false,
        readOnly = true,
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

@Composable
fun DateSelector(
    selectedDate: LocalDate,
    onClick: () -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    OutlinedTextField(
        value = selectedDate.format(formatter),
        onValueChange = {},
        label = { Text("Check-in Date") },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        enabled = false,
        readOnly = true,
        trailingIcon = {
            Icon(Icons.Rounded.CalendarToday, contentDescription = null)
        },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}
