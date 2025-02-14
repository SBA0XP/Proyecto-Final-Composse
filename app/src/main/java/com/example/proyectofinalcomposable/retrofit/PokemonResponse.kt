package com.example.proyectofinalcomposable.retrofit

data class PokemonResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites
)

data class Sprites(
    val front_default: String
)
