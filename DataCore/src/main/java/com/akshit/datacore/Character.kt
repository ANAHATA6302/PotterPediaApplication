package com.akshit.datacore

/**
 *  A data class that holds all the data for the character
 *  @param [id] is used for characterId
 *  @param [name] the character's name
 *  @param [actorName] the actor's name
 *  @param [species] the character's species
 *  @param [gender] the character's gender
 *  @param [house] the house they belong to, can be nullable
 *  @param [dateOfBirth] character's dat of birth
 *  @param [patronus] character's charm, can be nullable
 *  @param [alive] if they are alive
 *  @param [wand] character's wand and all details
 *  @param [imageUrl] character's image, can be null
 */
class Character (
    val id: String,
    val name: String,
    val actorName: String,
    val species: String,
    val gender: String,
    val house: String?,
    val dateOfBirth: String,
    val patronus: String,
    val alive: String,
    val wand: Wand?,
    val imageUrl: String? = null
)

class Wand(
    val wood: String,
    val core: String,
    val length: String,
)
