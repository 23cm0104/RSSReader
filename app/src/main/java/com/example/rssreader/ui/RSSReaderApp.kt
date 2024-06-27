package com.example.rssreader.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rssreader.R
import com.example.rssreader.ui.screens.HomeScreen
import com.example.rssreader.ui.screens.ItemsViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RSSReaderApp() {
    Scaffold(modifier = Modifier, topBar = {
        CenterAlignedTopAppBar(title = {
            Text(stringResource(R.string.student_name))
        })
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val itemsViewModel: ItemsViewModel = viewModel(factory = ItemsViewModel.Factory)
            val options = listOf(
                "日本🇯🇵",
                "米国🇺🇸",
                "中国🇨🇳"
            )
            var expanded by remember {
                mutableStateOf(false)
            }
            var selectedOptionText by remember { mutableStateOf(options[0]) }
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            var currentTime = LocalDateTime.now().format(formatter)
            Column {
                Text(modifier = Modifier.padding(8.dp), text = "$currentTime 更新")
                ExposedDropdownMenuBox(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }) {
                    TextField(
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        readOnly = true,
                        value = selectedOptionText,
                        onValueChange = { },
                        label = { Text("Option") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
                        expanded = false
                    }) {
                        options.forEachIndexed { index, s ->
                            DropdownMenuItem(text = { Text(s) }, onClick = {
                                selectedOptionText = options[index]
                                itemsViewModel.getItems(index)
                                currentTime = LocalDateTime.now().format(formatter)
                                expanded = false
                            })
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                HomeScreen(itemsViewModel = itemsViewModel)

            }

        }
    }
}