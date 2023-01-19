package com.example.reminder.ui
/*
Note("Phasellus elementum tortor ac sem semper, id tristique nulla consectetur."),
Note("Nam dapibus urna a justo bibendum vestibulum."),
Note("Suspendisse vitae mi euismod, egestas tortor et, mollis tellus."),
Note("Morbi in elit congue dolor auctor rutrum ac nec nulla."),
Note("Morbi condimentum nunc ut lectus posuere vehicula."),
Note("Quisque id tortor ac mauris mattis finibus non a mi."),
Note("Sed finibus dui sit amet massa pulvinar molestie."),
Note("Nulla imperdiet odio sed elit volutpat, at rutrum mauris tempus."),
Note("Phasellus quis urna ac dui tincidunt tincidunt."),
Note("Duis id libero quis neque suscipit tristique vitae varius dolor."),
Note("Aliquam sed neque nec justo congue porttitor."),
Note("Sed laoreet mi in mollis scelerisque."),
Note("In rutrum mauris lacinia feugiat tempus."),
Note("Fusce a nisi eu orci aliquam malesuada ac at lorem."),
Note("Nullam eget diam ullamcorper, finibus nulla sed, ullamcorper ante."),
Note("Mauris lobortis magna ac diam pulvinar, eu consectetur nisl efficitur."),
Note("Aliquam et lorem vitae orci interdum vehicula."),
Note("Morbi id nisl at nunc finibus elementum vitae id dolor."),
Note("Quisque a ex gravida, mollis libero in, ultrices mauris."),
Note("Nam semper urna id ligula tincidunt, at placerat nulla fermentum."),
Note("Suspendisse in tortor faucibus, viverra lacus sit amet, placerat dui."),
Note("Morbi consequat lectus at ante efficitur gravida."),
Note("Sed malesuada turpis vehicula placerat molestie."),
Note("Praesent et lectus ornare, placerat erat eget, consequat odio."),
Note("Nulla vitae metus eget nisi porta placerat."),
Note("Nam id felis id mauris placerat congue sit amet nec ante."),
Note("Nullam vehicula augue at lacinia lobortis."),
Note("Ut fringilla ipsum vitae risus vestibulum, eu tristique leo finibus."),
Note("Quisque volutpat magna sed lectus lobortis fringilla ac venenatis quam."),
Note("Integer id justo vel urna tincidunt aliquam eget non purus."),
Note("Mauris consequat dolor et elit convallis mattis."),
Note("Morbi ut ipsum semper, porta justo eget, interdum mi."),
Note("In laoreet libero eget ligula lacinia, vel auctor eros luctus."),
Note("Cras pharetra ex a bibendum molestie.")*/


/*
@Composable
fun ListItem(headline: String, supportText: String) {
    var isExpanded by remember { mutableStateOf(false) }

    Surface(
        Modifier.clickable { isExpanded = !isExpanded },
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(Modifier.padding(start = 16.dp, end = 24.dp)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.AccountCircle,
                        null,
                        Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.height(72.dp), Arrangement.Center) {
                        Text(
                            headline,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            supportText, color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Icon(
                    Icons.Rounded.PhoneInTalk,
                    null,
                    Modifier
                        .padding(start = 16.dp)
                        .size(24.dp),
                    MaterialTheme.colorScheme.primary
                )
            }
            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}
*/