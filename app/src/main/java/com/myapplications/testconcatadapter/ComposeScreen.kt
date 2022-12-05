package com.myapplications.testconcatadapter

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun ComposeScreen(
    currencySearchViewModel: SearchViewModel,
    onNavigateBack: () -> Unit
) {
    val searchState by rememberFlowWithLifecycle(currencySearchViewModel.userSearchModelState).collectAsState(
        initial = SearchModelState.empty
    )

    val headerNames = listOf("Recent", "Rest") //TODO

    CurrencyScreenContent(
        searchText = searchState.searchText,
        onSearchTextChanged = { currencySearchViewModel.onSearchTextChanged(it) },
        onClearClick = { currencySearchViewModel.onClearClick() },
        onNavigateBack = onNavigateBack,
        headerNames = headerNames,
        combinedList = listOf(searchState.recentCurrencies, searchState.restCurrencies)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrencyScreenContent(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onClearClick: () -> Unit,
    onNavigateBack: () -> Unit,
    headerNames: List<String>,
    combinedList: List<List<Currency>>
) {
    Scaffold(topBar = {
        MyTopAppBar(
            searchText = searchText,
            placeholderText = "Search currencies", //TODO
            onSearchTextChanged = onSearchTextChanged,
            onClearClick = onClearClick,
            onNavigateBack = onNavigateBack
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (combinedList[0].isEmpty() && combinedList[1].isEmpty()) { //TODO
                NoSearchResults()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (i in 0..1) {
                        if (combinedList[i].isNotEmpty()) {
                            stickyHeader {
                                Text(
                                    text = headerNames[i],
                                    color = Color.Blue,
                                    modifier = Modifier
                                        .background(Color.Gray)
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )
                                Divider(
                                    Modifier
                                        .height(Dp.Hairline)
                                        .fillMaxWidth()
                                )
                            }
                            items(
                                items = combinedList[i]
                            ) { currency ->
                                Text(
                                    text = getAnnotatedString(
                                        currency.name,
                                        searchText,
                                        SpanStyle(Color.Red)
                                    ), modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getAnnotatedString(
    fullText: String,
    constraint: String,
    spanStyle: SpanStyle,
): AnnotatedString {
    if (constraint.isEmpty()) {
        return AnnotatedString(fullText)
    } else {
        val builder = AnnotatedString.Builder(fullText)
        val length = constraint.length
        val listStartsAndEnds = mutableListOf<Pair<Int, Int>>()
        var index = fullText.indexOf(constraint)
        while (index >= 0) {
            val pair = Pair(index, index + length)
            listStartsAndEnds.add(pair)
            index = fullText.indexOf(constraint, index + length)
        }
        if (listStartsAndEnds.isEmpty()) {
            return AnnotatedString(fullText)
        }
        listStartsAndEnds.forEach { startEndPair ->
            builder.addStyle(spanStyle, startEndPair.first, startEndPair.second)
        }
        return builder.toAnnotatedString()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyTopAppBar(
    searchText: String,
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    TopAppBar(
        title = {
            Text(text = "My Title")
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    modifier = Modifier,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .onFocusChanged { focusState ->
                        showClearButton = (focusState.isFocused)
                    }
                    .focusRequester(focusRequester),
                value = searchText,
                onValueChange = onSearchTextChanged,
                placeholder = {
                    Text(text = placeholderText)
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                ),
                trailingIcon = {
                    AnimatedVisibility(
                        visible = showClearButton,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = {
                            onClearClick()
                            focusManager.clearFocus()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                })
            )
        },
    )
}

@Composable
fun NoSearchResults() {

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text("No matches found")
    }
}

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}