package com.example.a23100211practicacalificada2.data.remote.apiCards

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeckApiService {
    @GET("/api/deck/new/shuffle/")
    suspend fun newDeck(@Query("deck_count") deckCount: Int = 1): Response<DeckResponse>

    @GET("/api/deck/{deck_id}/draw/")
    suspend fun drawCards(@Path("deck_id") deckId: String, @Query("count") count: Int): Response<DrawResponse>
}

