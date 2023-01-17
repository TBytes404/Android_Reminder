@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.example.reminder.ui

import android.os.CountDownTimer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reminder.Note
import com.example.reminder.NotesViewModel
import com.example.reminder.ui.theme.ReminderTheme
import kotlinx.coroutines.launch
import java.util.*


private fun capitalize(word: String?): String {
    return word?.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    } ?: "Master"
}

@Composable
fun NoteItem(note: Note, onAction: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    Surface(
        Modifier.clickable { isExpanded = !isExpanded },
        MaterialTheme.shapes.large,
        shadowElevation = 1.dp
    ) {
        Column {
            ListItem({
                Surface(
                    Modifier
                        .animateContentSize()
                        .padding(1.dp)
                ) {
                    Text(
                        note.note, maxLines = if (isExpanded) Int.MAX_VALUE else 1
                    )
                }
            }, overlineText = { Text(note.formattedTime()) }, leadingContent = {
                Surface(
                    Modifier.size(40.dp), CircleShape, MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(
                        Icons.Rounded.Person,
                        null,
                        Modifier.padding(4.dp),
                        MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }, trailingContent = {
                IconButton(onAction) {
                    Icon(
                        Icons.Rounded.PhoneInTalk,
                        null,
                    )
                }
            })
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}

@Composable
fun WelcomeDialog(
    isOpen: Boolean,
    onClose: () -> Unit,
    accountName: String?,
    onAccountNameChange: (String?) -> Unit
) {
    var done by remember { mutableStateOf(false) }

    AnimatedVisibility(isOpen) {
        var name by remember { mutableStateOf(accountName ?: "") }
        AlertDialog(onDismissRequest = onClose,
            confirmButton = {
                TextButton(onClick = {
                    name = name.trim()
                    if (name == "") {
                        onAccountNameChange(null)
                        onClose()
                    } else {
                        onAccountNameChange(name)
                        onClose()
                        done = true
                    }
                }) { Text("Ok") }
            },
            dismissButton = { TextButton(onClick = onClose) { Text("Dismiss") } },
            icon = { Icon(Icons.Rounded.Badge, null) },
            title = { Text("Welcome ${capitalize(accountName)}!") },
            text = {
                Column {
                    Text("I am your Personal Call Assistant!! 👩🏻‍⚕️️")
                    Spacer(Modifier.height(16.dp))
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("State your Nickname") },
                        placeholder = { Text("Nickname") },
                        singleLine = true,
                        shape = MaterialTheme.shapes.large
                    )
                }
            })
    }

    AnimatedVisibility(done) {
        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (!done) cancel()
            }

            override fun onFinish() {
                done = false
                cancel()
            }
        }.start()
        AlertDialog(onDismissRequest = { done = false },
            confirmButton = { TextButton({ done = false }) { Text("Ok") } },
            icon = { Icon(Icons.Rounded.SupportAgent, null) },
            title = {
                Text(
                    "All Done ${capitalize(accountName)}!"
                )
            },
            text = {
                Text(
                    "I shall serve you well! 👩🏻‍⚕️",
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            })
    }
}


@Composable
fun CreateNoteDialog(isOpen: Boolean, onClose: () -> Unit, onCreate: (Note) -> Unit) {
    var note by remember { mutableStateOf("") }
//    val datePickerState = rememberDatePickerState()
//    var showTimePicker by remember { mutableStateOf(false) }

    AnimatedVisibility(visible = isOpen) {
        var isError by remember { mutableStateOf(false) }
        AlertDialog(onDismissRequest = onClose, confirmButton = {
            TextButton({
                note = note.trim()
                if (note == "") isError = true else {
                    isError = false
                    onCreate(Note(note))
                    note = ""
                    onClose()
                }
            }) { Text("Ok") }
        }, dismissButton = {
            TextButton(onClick = onClose) { Text("Dismiss") }
        }, icon = { Icon(Icons.Rounded.EditNote, null) }, title = {
            Text("Create a new Note!")
        }, text = {
            Column {
                TextField(
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

@Composable
fun TopBar(
    headline: String, scrollBehavior: TopAppBarScrollBehavior, onClickProfile: () -> Unit
) {
    LargeTopAppBar(
        title = { Text(headline) }, colors = TopAppBarDefaults.largeTopAppBarColors(
            MaterialTheme.colorScheme.primaryContainer
        ), actions = {
            IconButton(onClick = onClickProfile) {
                Icon(Icons.Rounded.DriveFileRenameOutline, null)
            }
        }, scrollBehavior = scrollBehavior
    )
}

@Composable
fun CreateNoteFab(expanded: Boolean, onOpenCreateNoteDialog: () -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text("Add Note") },
        icon = { Icon(Icons.Rounded.Create, null) },
        onClick = onOpenCreateNoteDialog,
        expanded = expanded
    )
}

@Composable
fun NotesList(
    notes: List<Note>, listState: LazyListState, snackBarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()

    LazyColumn(state = listState) {
        item { Spacer(Modifier.height(8.dp)) }
        items(notes) { note ->
            NoteItem(note) {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        "Can't Call Yet!", "Ok", true, SnackbarDuration.Short
                    )
                }
            }
        }
        item { Spacer(Modifier.height(8.dp)) }
    }
}

@Composable
fun NotesView(notesViewModel: NotesViewModel = viewModel()) {
    val listState = rememberLazyListState()
    val snackBarHostState = remember { SnackbarHostState() }

    var openCreateNoteDialog by remember { mutableStateOf(false) }
    var openWelcomeDialog by remember { mutableStateOf((notesViewModel.accountName == null)) }

    val appbarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appbarState)

    ReminderTheme {
        // A surface container using the 'background' color from the theme

        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            TopBar(headline = "Hello ${capitalize(notesViewModel.accountName)}!",
                scrollBehavior,
                onClickProfile = { openWelcomeDialog = true })
        }, snackbarHost = { SnackbarHost(snackBarHostState) }, floatingActionButton = {
            CreateNoteFab(expanded = remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }.value,
                onOpenCreateNoteDialog = { openCreateNoteDialog = true })
        }) { scaffoldPadding ->
            Surface(
                Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                NotesList(notes = notesViewModel.notes.reversed(), listState, snackBarHostState)
                WelcomeDialog(
                    isOpen = openWelcomeDialog,
                    onClose = { openWelcomeDialog = false },
                    accountName = notesViewModel.accountName
                ) { notesViewModel.updateAccountName(it) }
                CreateNoteDialog(
                    isOpen = openCreateNoteDialog,
                    onClose = { openCreateNoteDialog = false }) { notesViewModel.createNote(it) }
            }
        }
    }
}


@Preview
@Composable
fun DefaultPreview() {
    NotesView()
}
