package com.mtv.based.core

import kotlinx.serialization.Serializable

@Serializable
data class CountryProbability(
    val country_id: String,
    val probability: Double
)

@Serializable
data class NameResponse(
    val count: Int,
    val name: String,
    val country: List<CountryProbability>
)
