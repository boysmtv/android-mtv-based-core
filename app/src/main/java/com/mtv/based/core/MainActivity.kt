package com.mtv.based.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.loading.LoadingV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: NetworkViewModel = hiltViewModel()
            val userState by viewModel.users.collectAsState()
            val postState by viewModel.createUserResponse.collectAsState()
            val baseUiState by viewModel.baseUiState.collectAsState()

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

                when (val current = userState) {
                    is Resource.Success -> {
                        Text("Name: ${current.data.name}")
                        Text("Count: ${current.data.count}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Countries:")
                        current.data.country.forEach { country ->
                            Text("${country.country_id} : ${country.probability}")
                        }
                    }

                    else -> {}
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

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Create New User",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.doLogin(
                            name = "Boy Santoso",
                            email = "boy@example.com",
                            age = 25
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create User (POST)")
                }

                Spacer(modifier = Modifier.height(8.dp))

                when (val postResult = postState) {
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

                    else -> {}
                }

            }
        }
    }
}
