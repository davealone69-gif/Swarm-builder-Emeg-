package com.davealone69.swarmbuilder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SwarmBuilderApp() }
    }
}

private val starterPrompt = "Build an Android app that..."

@androidx.compose.runtime.Composable
private fun SwarmBuilderApp() {
    var prompt by rememberSaveable { mutableStateOf("") }
    var status by rememberSaveable { mutableStateOf("Ready. Waiting for a build request.") }

    MaterialTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("🐝 Swarm Builder") }) }
        ) { padding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        "Text → Plan → Reuse → Build → Fix → Verify",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                item {
                    OutlinedTextField(
                        value = prompt,
                        onValueChange = { prompt = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 7,
                        label = { Text("Build request") },
                        placeholder = { Text(starterPrompt) }
                    )
                }
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = {
                            status = if (prompt.isBlank()) {
                                "Enter a build request first."
                            } else {
                                "Request accepted. Swarm planning engine is next to be connected."
                            }
                        }) { Text("Build") }
                        Button(onClick = { prompt = "" }) { Text("Clear") }
                    }
                }
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Status", style = MaterialTheme.typography.titleMedium)
                            Text(status, modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                }
                item {
                    Text(
                        "Truth mode: UI controls are not treated as implementation. The real swarm engine must be connected and verified before build capability is claimed.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
