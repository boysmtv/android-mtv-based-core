package com.mtv.based.core

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.uicomponent.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.component.dialog.dialogv1.ErrorDialogStateV1
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.OK_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.WARNING_STRING
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: NetworkViewModel = hiltViewModel()
            val state by viewModel.users.collectAsState()
            val postState by viewModel.createUserResponse.collectAsState()

            var showDialog by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) { viewModel.fetchUsers() }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text(
                    "User Info",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                when (val current = state) {
                    is Resource.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    is Resource.Success -> {
                        Text("Name: ${current.data.name}")
                        Text("Count: ${current.data.count}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Countries:")
                        current.data.country.forEach { country ->
                            Text("${country.country_id} : ${country.probability}")
                        }
                    }

                    is Resource.Error -> Text(
                        "Error: ${current.error.message}",
                        color = Color.Red
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Create New User",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.createUser(
                            name = "Boy Santoso",
                            email = "boy@example.com",
                            age = 25
                        )
                        showDialog = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create User (POST)")
                }

                Spacer(modifier = Modifier.height(8.dp))

                when (val postResult = postState) {
                    is Resource.Loading -> Text("Creating user...")

                    is Resource.Success -> {
                        val user = postResult.data
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                "User Created Successfully",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Name: ${user.name}")
                            Text("Count: ${user.count}")
                            Column {
                                Text("Countries:")
                                user.country.forEach { c ->
                                    Text("${c.country_id} : ${c.probability}")
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        Log.e("ERROR-BOYS", "MSG: ${postResult.error.message}")
                        Text(
                            "Failed: ${postResult.error.message}",
                            color = Color.Red
                        )

                        if (showDialog) {
                            DialogCenterV1(
                                state = ErrorDialogStateV1(
                                    title = WARNING_STRING,
                                    message = postResult.error.message,
                                    primaryButtonText = OK_STRING
                                ),
                                onDismiss = { showDialog = false }
                            )
                        }
                    }
                }

            }
        }
    }
}
