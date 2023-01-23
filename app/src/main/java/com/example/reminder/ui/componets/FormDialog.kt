@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.example.reminder.ui.componets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.reminder.data.Note
import com.marosseleng.compose.material3.datetimepickers.time.ui.TimePicker
import java.time.*
import java.time.format.DateTimeFormatter

//@Preview(showBackground = true)
//@Composable
//fun FormDialogPreview() {
//    ReminderTheme {
//        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//            CreateNoteDialog(isOpen = true, onClose = {}, onCreate = {})
//        }
//    }
//}

@Composable
fun CreateNoteDialog(isOpen: Boolean, onClose: () -> Unit, onCreate: (Note) -> Unit) {

    var showTimePicker by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
    val now = selectedTime.format(timeFormatter)

    val instantDate = LocalDate.now()
    val datePickerState =
        rememberDatePickerState(
//            initialSelectedDateMillis = instantDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(),
            yearsRange = instantDate.year..instantDate.year + 1
        )


    var showDatePicker by remember { mutableStateOf(false) }
    fun selectedDate(): LocalDate {
        return if (datePickerState.selectedDateMillis == null) instantDate
        else
            Instant.ofEpochMilli(datePickerState.selectedDateMillis!!).atZone(ZoneId.of("UTC"))
                .toLocalDate()
    }

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")
    val today = selectedDate().format(dateFormatter)

    var note by remember { mutableStateOf("") }


//    Time Picker Dialog
    AnimatedVisibility(visible = showTimePicker) {
//        TimePickerDialog(onDismissRequest = { showTimePicker = false },
//            onTimeChange = { selectedTime = it; showTimePicker = false })

        DateTimePickerDialogCustom(icon = Icons.Rounded.Schedule,
            onClose = { showTimePicker = false }) {
            val instantTime = LocalTime.now()
            TimePicker(initialTime = instantTime, onTimeChange = { selectedTime = it })
        }
    }

//    Date Picker Dialog
    AnimatedVisibility(visible = showDatePicker) {
//        DatePickerDialog(onDismissRequest = { showDatePicker = false },
//            onDateChange = { selectedDate = it; showDatePicker = false })

//        LaunchedEffect(datePickerState) { selectedDate = convertMillisToDate() }

        DateTimePickerDialogCustom(icon = Icons.Rounded.EditCalendar,
            onClose = { showDatePicker = false }) {
            DatePicker(datePickerState,
                dateValidator = { utcDateInMills ->
                    val selected =
                        Instant.ofEpochMilli(utcDateInMills).atZone(ZoneId.of("UTC")).toLocalDate()
                    selected >= instantDate
                })
        }
    }



    AnimatedVisibility(visible = isOpen) {
        var isError by remember { mutableStateOf(false) }
        AlertDialog(onDismissRequest = onClose, confirmButton = {
            Button({
                if (note.isBlank()) isError = true else {
                    isError = false
                    onCreate(Note(note.trim(), LocalDateTime.of(selectedDate(), selectedTime)))
                    note = ""
                    onClose()
                }
            }) { Text("Ok") }
        }, modifier = Modifier.wrapContentHeight(), dismissButton = {
            TextButton(onClick = onClose) { Text("Dismiss") }
        }, icon = { Icon(Icons.Rounded.EditNote, null) }, title = {
            Text("Create a new Note!")
        }, text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = note, onValueChange = { note = it },
                    label = { Text("New note") }, placeholder = { Text("eg. Call him tomorrow.") },
                    leadingIcon = { Icon(Icons.Rounded.EditNote, null) },
                    trailingIcon = {
                        AnimatedVisibility(visible = note.isNotBlank()) {
                            IconButton(onClick = { note = "" }) { Icon(Icons.Rounded.Clear, null) }
                        }
                        AnimatedVisibility(visible = isError) { Icon(Icons.Rounded.Error, null) }
                    },
                    supportingText = {
                        AnimatedVisibility(visible = isError) { Text("Put some text!") }
                    },
                    isError = isError, shape = MaterialTheme.shapes.large,
                )


                OutlinedDialogButton(
                    key = "time",
                    value = now,
                    onValueChange = {},
                    onClick = { showTimePicker = true },
                    icon = Icons.Rounded.Schedule,
                    isError
                )

                OutlinedDialogButton(
                    key = "date",
                    value = today,
                    onValueChange = {},
                    onClick = { showDatePicker = true },
                    icon = Icons.Rounded.EditCalendar,
                    isError
                )

            }
        }, properties = DialogProperties(usePlatformDefaultWidth = false))
    }
}


@Composable
fun DateTimePickerDialogCustom(
    icon: ImageVector, onClose: () -> Unit, content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = { Button(onClick = onClose) { Text(text = "Ok") } },
        dismissButton = { TextButton(onClick = onClose) { Text(text = "Dismiss") } },
        icon = { Icon(icon, null) },
        text = content,// properties = DialogProperties(usePlatformDefaultWidth = false)
    )
}


@Composable
fun OutlinedDialogButton(
    key: String,
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    icon: ImageVector,
    isError: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.clickable(onClick = onClick),
        label = { Text("${capitalize(key)} to reminder") },
        enabled = false, readOnly = true,
        placeholder = { Text("eg. $value") },
        leadingIcon = { Icon(icon, null) },
        trailingIcon = { Icon(Icons.Rounded.ExpandMore, null) },
        supportingText = {
            AnimatedVisibility(visible = isError) { Text("Select a ${key.lowercase()}!") }
        },
        isError = isError, singleLine = true,
        shape = MaterialTheme.shapes.large,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    )
}

/*
@Composable
fun CreateNoteDialog(isOpen: Boolean, onClose: () -> Unit, onCreate: (Note) -> Unit) {
    var note by remember { mutableStateOf("") }
//    val datePickerState = rememberDatePickerState()
//    var showTimePicker by remember { mutableStateOf(false) }

    AnimatedVisibility(visible = isOpen) {
        var isError by remember { mutableStateOf(false) }
        AlertDialog(onDismissRequest = onClose, confirmButton = {
            Button({
                if (note.isBlank()) isError = true else {
                    isError = false
                    onCreate(Note(note.trim()))
                    note = ""
                    onClose()
                }
            }) { Text("Ok") }
        }, modifier = Modifier.wrapContentHeight(), dismissButton = {
            TextButton(onClick = onClose) { Text("Dismiss") }
        }, icon = { Icon(Icons.Rounded.EditNote, null) }, title = {
            Text("Create a new Note!")
        }, text = {
            Column {
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("New Note") },
                    placeholder = { Text("eg. Call Him Tomorrow.") },
                    supportingText = {
                        AnimatedVisibility(visible = isError) {
                            Text("Can't create Empty note!")
                        }
                    },
                    isError = isError,
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                )

//            Spacer(Modifier.width(8.dp))
//            TextButton({ showTimePicker = false }, Modifier.fillMaxWidth()) {
//                Row(verticalAlignment = Alignment.CenterVertically){
//                    Icon(Icons.Rounded.Timer, null)
//                    Spacer(Modifier.width(8.dp))
//                    Text("Pick the Time", style = MaterialTheme.typography.bodySmall)
//                }
//            }
            }
        }, properties = DialogProperties(usePlatformDefaultWidth = false))
    }
}
*/