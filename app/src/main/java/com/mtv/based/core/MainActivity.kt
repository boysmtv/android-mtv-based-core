package com.mtv.based.core

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mtv.based.core.model.LoginRequest
import com.mtv.based.core.network.firebase.result.FirebaseResult
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.loading.LoadingV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current

            val viewModel: NetworkViewModel = hiltViewModel()
            val userState by viewModel.getUser.collectAsState()
            val postState by viewModel.createUser.collectAsState()
            val postLogin by viewModel.postLogin.collectAsState()
            val userFirebase by viewModel.userFirebase.collectAsState()
            val saveUserFirebase by viewModel.saveUserFirebase.collectAsState()
            val baseUiState by viewModel.baseUiState.collectAsState()

            // --- One time action ---
            LaunchedEffect(Unit) {
                viewModel.fetchUsers()
                viewModel.saveUser("1", "Boys", "Boys.mtv@gmail.com")
            }

            // --- Side Effects ---
            LaunchedEffect(userFirebase) {
                if (userFirebase is FirebaseResult.Success) {
                    val data = (userFirebase as FirebaseResult.Success).data
                    Toast.makeText(context, "Success Fetch - $data", Toast.LENGTH_LONG).show()
                    Log.e("LOG-FIREBASE", "Success-Fetch: $data")
                }
            }

            LaunchedEffect(saveUserFirebase) {
                if (saveUserFirebase is FirebaseResult.Success) {
                    val data = (saveUserFirebase as FirebaseResult.Success).data
                    Toast.makeText(context, "Success Post to Firebase - $data", Toast.LENGTH_LONG).show()
                    Log.e("LOG-FIREBASE", "Success-Post: $data")
                    viewModel.fetchUserFirebase("1")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text("User Info", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))

                // UI Display Only (no side effects)
                if (userState is Resource.Success) {
                    val data = (userState as Resource.Success).data
                    Text("Name: ${data.name}")
                    Text("Count: ${data.count}")
                    Spacer(Modifier.height(8.dp))

                    Text("Countries:")
                    data.country.forEach {
                        Text("${it.country_id} : ${it.probability}")
                    }
                }

                if (baseUiState.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Black.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingV2()
                    }
                }

                baseUiState.errorDialog?.let {
                    DialogCenterV1(
                        state = it,
                        onDismiss = viewModel::dismissError
                    )
                }

                Spacer(Modifier.height(16.dp))

                // --- Create User ---
                Text("Create New User", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.doLogin(
                            LoginRequest(
                                username = "Boys",
                                password = "Mtv"
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create User (POST)")
                }

                Spacer(Modifier.height(8.dp))

                if (postState is Resource.Success) {
                    val user = (postState as Resource.Success).data
                    Column(Modifier.padding(8.dp)) {
                        Text("User Created Successfully", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text("Name: ${user.name}")
                        Text("Count: ${user.count}")
                        Text("Countries:")
                        user.country.forEach {
                            Text("${it.country_id} : ${it.probability}")
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                if (postLogin is Resource.Success) {
                    val user = (postLogin as Resource.Success).data
                    Column(Modifier.padding(8.dp)) {
                        Text("Login Successfully", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text("First Name: ${user.firstname}")
                        Text("Last Name: ${user.lastname}")
                    }
                }
            }
        }
    }
}
