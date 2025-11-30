package com.example.a23100211practicacalificada2.data.repository

import com.example.a23100211practicacalificada2.data.remote.apiCards.DrawResponse
import com.example.a23100211practicacalificada2.data.remote.apiCards.DeckResponse
import com.example.a23100211practicacalificada2.data.remote.apiCards.RetrofitDeckInstance

class DeckRepository {
    private val api = RetrofitDeckInstance.api

    suspend fun createDeck(): DeckResponse? {
        val resp = api.newDeck(1)
        return if (resp.isSuccessful) resp.body() else null
    }

    suspend fun draw(deckId: String, count: Int): DrawResponse? {
        val resp = api.drawCards(deckId, count)
        return if (resp.isSuccessful) resp.body() else null
    }
}

