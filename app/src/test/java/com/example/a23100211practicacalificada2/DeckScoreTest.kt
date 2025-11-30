package com.example.a23100211practicacalificada2

import com.example.a23100211practicacalificada2.data.remote.apiCards.CardModel
import com.example.a23100211practicacalificada2.presentation.apiCards.DeckGameViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class DeckScoreTest {

    @Test
    fun testCalculateScore_numbersAndFacesAndAce() {
        val vm = DeckGameViewModel()
        val cards = listOf(
            CardModel("AS", "", emptyMap(), "A", "SPADES"),
            CardModel("10H", "", emptyMap(), "10", "HEARTS"),
            CardModel("JC", "", emptyMap(), "J", "CLUBS"),
        )
        val score = vm.calculateScore(cards)
        // A=11, 10=10, J=10 => 31
        assertEquals(31, score)
    }

    @Test
    fun testCalculateScore_onlyNumbers() {
        val vm = DeckGameViewModel()
        val cards = listOf(
            CardModel("2S", "", emptyMap(), "2", "SPADES"),
            CardModel("3H", "", emptyMap(), "3", "HEARTS"),
        )
        val score = vm.calculateScore(cards)
        assertEquals(5, score)
    }

    @Test
    fun testCalculateScore_faceCardsAreTen() {
        val vm = DeckGameViewModel()
        val cards = listOf(
            CardModel("JS", "", emptyMap(), "J", "SPADES"),
            CardModel("QH", "", emptyMap(), "Q", "HEARTS"),
            CardModel("KD", "", emptyMap(), "K", "DIAMONDS")
        )
        val score = vm.calculateScore(cards)
        // J=10, Q=10, K=10 => 30
        assertEquals(30, score)
    }

    @Test
    fun testCalculateScore_fullNamesFaceCardsAndAce() {
        val vm = DeckGameViewModel()
        val cards = listOf(
            CardModel("JACK1", "", emptyMap(), "JACK", "SPADES"),
            CardModel("QUEEN1", "", emptyMap(), "QUEEN", "HEARTS"),
            CardModel("KING1", "", emptyMap(), "KING", "DIAMONDS"),
            CardModel("ACE1", "", emptyMap(), "ACE", "CLUBS")
        )
        val score = vm.calculateScore(cards)
        // JACK=10, QUEEN=10, KING=10, ACE=11 => 41
        assertEquals(41, score)
    }
}
