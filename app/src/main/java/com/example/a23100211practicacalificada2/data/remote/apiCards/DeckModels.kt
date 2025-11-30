package com.example.a23100211practicacalificada2.data.remote.apiCards

import com.google.gson.annotations.SerializedName

data class DeckResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("deck_id") val deck_id: String,
    @SerializedName("remaining") val remaining: Int,
    @SerializedName("shuffled") val shuffled: Boolean
)

data class DrawResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("cards") val cards: List<CardModel>,
    @SerializedName("deck_id") val deck_id: String,
    @SerializedName("remaining") val remaining: Int
)

data class CardModel(
    @SerializedName("code") val code: String,
    @SerializedName("image") val image: String,
    @SerializedName("images") val images: Map<String, String>,
    @SerializedName("value") val value: String,
    @SerializedName("suit") val suit: String
)

