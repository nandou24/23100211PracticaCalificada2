package com.example.a23100211practicacalificada2.presentation.apiCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.a23100211practicacalificada2.data.repository.DeckRepository

@Composable
fun DeckGameScreen(modifier: Modifier = Modifier, viewModel: DeckGameViewModel = viewModel()) {
    val deckId = viewModel.deckId.collectAsState()
    val playerCards = viewModel.playerCards.collectAsState()
    val playerScore = viewModel.playerScore.collectAsState()
    val machineNumber = viewModel.machineNumber.collectAsState()
    val result = viewModel.result.collectAsState()
    val loading = viewModel.loading.collectAsState()
    val error = viewModel.error.collectAsState()

    val selectedCount = remember { mutableStateOf(2) }

    Column(modifier = modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Deck of Cards - Mini Blackjack")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.startNewGame() }) {
            Text(text = "Nueva partida")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Deck ID: ${deckId.value ?: "-"}")
        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Cartas a tomar:")
            for (i in 2..5) {
                Button(onClick = { selectedCount.value = i }) {
                    Text(text = i.toString())
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { viewModel.drawPlayerCards(selectedCount.value) }) {
            Text(text = "Pedir cartas (${selectedCount.value})")
        }
        Spacer(modifier = Modifier.height(12.dp))

        if (loading.value) {
            CircularProgressIndicator()
        }

        error.value?.let { Text(text = "Error: $it") }

        Text(text = "Jugador - Puntaje: ${playerScore.value ?: "-"}")
        Text(text = "Máquina - Puntaje: ${machineNumber.value ?: "-"}")
        result.value?.let { Text(text = "Resultado: $it") }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(playerCards.value) { card ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // show image if available
                    AsyncImage(model = card.image, contentDescription = "card image")
                    Text(text = "${card.value} ${card.suit}")
                }
            }
        }
    }
}
