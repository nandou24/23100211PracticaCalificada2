package com.example.a23100211practicacalificada2.presentation.apiCards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a23100211practicacalificada2.data.remote.apiCards.CardModel
import com.example.a23100211practicacalificada2.data.repository.DeckRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class DeckGameViewModel(private val repository: DeckRepository = DeckRepository()) : ViewModel() {

    private val _deckId = MutableStateFlow<String?>(null)
    val deckId: StateFlow<String?> = _deckId.asStateFlow()

    private val _playerCards = MutableStateFlow<List<CardModel>>(emptyList())
    val playerCards: StateFlow<List<CardModel>> = _playerCards.asStateFlow()

    private val _playerScore = MutableStateFlow<Int?>(null)
    val playerScore: StateFlow<Int?> = _playerScore.asStateFlow()

    private val _machineNumber = MutableStateFlow<Int?>(null)
    val machineNumber: StateFlow<Int?> = _machineNumber.asStateFlow()

    private val _result = MutableStateFlow<String?>(null)
    val result: StateFlow<String?> = _result.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun startNewGame() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            _result.value = null
            _playerCards.value = emptyList()
            _playerScore.value = null
            // create deck
            val deckResp = repository.createDeck()
            if (deckResp == null) {
                _error.value = "No se pudo crear la baraja"
                _loading.value = false
                return@launch
            }
            _deckId.value = deckResp.deck_id
            // generate machine number between 16 and 21
            _machineNumber.value = Random.nextInt(16, 22)
            _loading.value = false
        }
    }

    fun drawPlayerCards(count: Int) {
        val deck = _deckId.value ?: run {
            _error.value = "Baraja no inicializada"
            return
        }
        if (count < 2 || count > 5) {
            _error.value = "Elige entre 2 y 5 cartas"
            return
        }
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            val drawResp = repository.draw(deck, count)
            if (drawResp == null) {
                _error.value = "Error al pedir cartas"
                _loading.value = false
                return@launch
            }
            _playerCards.value = drawResp.cards
            val score = calculateScore(drawResp.cards)
            _playerScore.value = score
            // determine result
            val machine = _machineNumber.value ?: 0
            _result.value = determineResult(score, machine)
            _loading.value = false
        }
    }

    // Rules: J/Q/K = 10, A = 11, numbers numeric
    fun calculateScore(cards: List<CardModel>): Int {
        var sum = 0
        for (c in cards) {
            val v = c.value.trim().uppercase()
            sum += when (v) {
                "J", "Q", "K", "JACK", "QUEEN", "KING" -> 10
                "A", "ACE" -> 11
                else -> v.toIntOrNull() ?: 0
            }
        }
        return sum
    }

    fun determineResult(playerScore: Int, machine: Int): String {
        return when {
            playerScore > 21 && machine > 21 -> "Empate (ambos se pasan)"
            playerScore > 21 -> "Máquina gana (jugador se pasa)"
            machine > 21 -> "Jugador gana (máquina se pasa)"
            playerScore == machine -> "Empate"
            playerScore > machine -> "Jugador gana"
            else -> "Máquina gana"
        }
    }
}
