@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)

package com.example.reminder.ui.componets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.reminder.data.Note
import com.example.reminder.ui.NotesViewModel
import com.example.reminder.ui.theme.ReminderTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.time.Duration.Companion.seconds


fun capitalize(word: String?): String {
    return word?.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    } ?: "Master"
}


@Composable
fun NoteItem(note: Note, modifier: Modifier = Modifier, action: @Composable () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier.clickable { isExpanded = !isExpanded },
        shape = MaterialTheme.shapes.large,
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
            }, trailingContent = action)
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}

@Composable
fun WelcomeDialog(
    isOpen: Boolean,
    onClose: () -> Unit,
    accountName: String?,
    prefersDarkTheme: Boolean,
    onAccountNameChange: (String?) -> Unit,
    onThemePreferenceChange: (Boolean) -> Unit
) {
    var done by remember { mutableStateOf(false) }

    AnimatedVisibility(isOpen) {
        var name by remember { mutableStateOf(accountName ?: "") }
        AlertDialog(
            onDismissRequest = onClose,
            confirmButton = {
                Button(onClick = {
                    name = name.trim()
                    onAccountNameChange(if (name == "") null else name)
                    onClose()
                    done = true
                }) { Text("Ok") }
            },
            dismissButton = { TextButton(onClick = onClose) { Text("Dismiss") } },
            icon = { Icon(Icons.Rounded.Badge, null) },
            title = { Text("Welcome ${capitalize(accountName)}!") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("I am your Personal Call Assistant!! 👩🏻‍⚕️️")

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("State your Nickname") },
                        trailingIcon = {
                            AnimatedVisibility(visible = name.isNotBlank()) {
                                IconButton(onClick = { name = "" }) {
                                    Icon(Icons.Rounded.Clear, null)
                                }
                            }
                        },
                        placeholder = { Text("Nickname") },
                        singleLine = true,
                        shape = MaterialTheme.shapes.large,
                    )
                }
            },
        )
    }

    AnimatedVisibility(done) {
//        object : CountDownTimer(4000, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                if (!done) cancel()
//            }
//
//            override fun onFinish() {
//                done = false
//                cancel()
//            }
//        }.start()
//        LaunchedEffect(accountName) { if(accountName.isNotBlank()) done = true }

        AlertDialog(onDismissRequest = { done = false },
            confirmButton = { TextButton({ done = false }) { Text("Ok") } },
            icon = { Icon(Icons.Rounded.SupportAgent, null) },
            title = {
                Text(
                    "All Done ${capitalize(accountName)}!"
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        "I shall serve you well! 👩🏻‍⚕️",
                        Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Dark Theme", style = MaterialTheme.typography.titleMedium)

                        Switch(checked = prefersDarkTheme,
                            onCheckedChange = onThemePreferenceChange,
                            thumbContent = {
                                Crossfade(prefersDarkTheme) {
                                    if (it) Icon(Icons.Rounded.DarkMode, null)
                                    else Icon(Icons.Rounded.LightMode, null)
                                }
                            })
                    }
                }
            })
    }
}

@Composable
fun DeleteNoteDialog(isOpen: Boolean, count: Int, onClose: () -> Unit, onDelete: () -> Unit) {
    AnimatedVisibility(visible = isOpen) {
        AlertDialog(onDismissRequest = onClose, confirmButton = {
            Button(
                onClick = {
                    onDelete()
                    onClose()
                }, colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text("Delete")
            }
        }, dismissButton = {
            TextButton(onClick = onClose) { Text("Cancel") }
        }, icon = { Icon(Icons.Rounded.DeleteSweep, null) }, title = {
            Text("Delete $count Note${if (count > 1) "s" else ""}!")
        }, text = { Text("Are you sure?") })
    }
}

@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    accountName: String?,
    prefersDarkTheme: Boolean,
    onChangeAccountName: (String?) -> Unit,
    onThemePreferenceChange: (Boolean) -> Unit,
    onClickDelete: () -> Unit,
) {
    var openWelcomeDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1.seconds)
        openWelcomeDialog = accountName == null
    }

    LargeTopAppBar(
        title = { Text("Hello ${capitalize(accountName)}!") },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            IconButton(onClick = onClickDelete) {
                Icon(Icons.Rounded.DeleteSweep, null)
            }
            IconButton(onClick = { openWelcomeDialog = true }) {
                Icon(Icons.Rounded.DriveFileRenameOutline, null)
            }
        },
        scrollBehavior = scrollBehavior
    )

    WelcomeDialog(
        isOpen = openWelcomeDialog,
        onClose = { openWelcomeDialog = false },
        accountName,
        prefersDarkTheme,
        onAccountNameChange = { onChangeAccountName(it) },
        onThemePreferenceChange
    )
}

@Composable
fun NoteActionFab(
    selectionMode: Boolean, expandedFab: Boolean, onOpenDialog: () -> Unit
) {
    ExtendedFloatingActionButton(text = {
        Crossfade(targetState = selectionMode) {
            if (it) Text(text = "Remove Note") else Text(text = "Add Note")
        }
    }, icon = {
        Crossfade(targetState = selectionMode) {
            if (it) Icon(
                Icons.Rounded.Delete, "remove note"
            ) else Icon(Icons.Rounded.Create, "add note")
        }
    }, onClick = onOpenDialog, expanded = expandedFab
    )
}

@Composable
fun NotesList(
    notes: List<Note>,
    listState: LazyListState,
    snackBarHostState: SnackbarHostState,
    selectionMode: Boolean,
    isChecked: (id: Int) -> Boolean,
    onChecked: (Int, Boolean) -> Unit,
) {
    val scope = rememberCoroutineScope()

    LazyColumn(state = listState, modifier = Modifier.animateContentSize()) {
        item { Spacer(Modifier.height(8.dp)) }
        items(notes, key = { it.id }) { note ->
            NoteItem(note, modifier = Modifier.animateItemPlacement()) {
                Crossfade(
                    targetState = selectionMode,
                    modifier = Modifier
                        .animateContentSize()
                        .animateItemPlacement()
                ) { selectable ->
                    if (selectable) {
                        var checked by remember { mutableStateOf(isChecked(note.id)) }
                        Checkbox(checked, onCheckedChange = {
                            checked = it
                            onChecked(note.id, it)
                        })
                    } else {
                        IconButton(onClick = {
                            scope.launch {
                                snackBarHostState.showSnackbar(
                                    "Can't Call Yet!", "Ok", true, SnackbarDuration.Short
                                )
                            }
                        }) { Icon(Icons.Rounded.PhoneInTalk, "call") }
                    }
                }
            }
        }
        item { Spacer(Modifier.height(96.dp)) }
    }
}

@Composable
fun NotesScreen(notesViewModel: NotesViewModel) {
    val accountUiState by notesViewModel.accountUiState.collectAsState()
    val notesUiState by notesViewModel.notesUiState.collectAsState()

    val listState = rememberLazyListState()
    val snackBarHostState = remember { SnackbarHostState() }

    var openCreateNoteDialog by remember { mutableStateOf(false) }
    var openDeleteNoteDialog by remember { mutableStateOf(false) }

    val appbarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appbarState)

    var selectionMode by remember { mutableStateOf(false) }
    val expandedFab by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    val selectedNotes = remember { mutableListOf<Int>() }

    ReminderTheme(darkTheme = accountUiState.prefersDarkTheme) {
        // A surface container using the 'background' color from the theme

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopBar(
                    scrollBehavior,
                    accountName = accountUiState.accountName,
                    prefersDarkTheme = accountUiState.prefersDarkTheme,
                    onChangeAccountName = { notesViewModel.saveAccountName(it) },
                    onThemePreferenceChange = { notesViewModel.saveThemePreference(it) },
                    onClickDelete = { selectionMode = !selectionMode },
                )
            },
            snackbarHost = { SnackbarHost(snackBarHostState) },
            floatingActionButton = {
                NoteActionFab(selectionMode, expandedFab) {
                    if (selectionMode) openDeleteNoteDialog = true else openCreateNoteDialog = true
                }
            },
        ) { scaffoldPadding ->
            Surface(
                Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                AnimatedVisibility(visible = notesUiState.notes.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Empty Reminder List!",
                            color = MaterialTheme.colorScheme.outline,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                AnimatedVisibility(visible = notesUiState.notes.isNotEmpty()) {
                    NotesList(notes = notesUiState.notes.reversed(),
                        listState,
                        snackBarHostState,
                        selectionMode,
                        isChecked = { selectedNotes.contains(it) }) { id, checked ->
                        if (checked) selectedNotes.add(id) else selectedNotes.remove(id)
                    }
                }

                CreateNoteDialog(isOpen = openCreateNoteDialog, onClose = {
                    openCreateNoteDialog = false
                }) { notesViewModel.createNote(it) }

                DeleteNoteDialog(
                    isOpen = openDeleteNoteDialog,
                    count = selectedNotes.size,
                    onClose = {
                        openDeleteNoteDialog = false
                        selectionMode = false
                    }) { notesViewModel.removeNotes(ids = selectedNotes.toIntArray()) }
            }
        }
    }
}


//@Preview
//@Composable
//fun DefaultPreview() {
//    NotesView()
//}
