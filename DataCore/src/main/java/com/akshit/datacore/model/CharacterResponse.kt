package com.akshit.datacore.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("alternate_names")
    val alternateName: List<String>? = null,
    @SerialName("species")
    val species: String? = null,
    @SerialName("gender")
    val gender: String? = null,
    @SerialName("house")
    val house: String? = null,
    @SerialName("dateOfBirth")
    val dateOfBirth: String? = null,
    @SerialName("yearOfBirth")
    val yearOfBirth: String? = null,
    @SerialName("wizard")
    val wizard: String? = null,
    @SerialName("ancestry")
    val ancestry: String? = null,
    @SerialName("eyeColour")
    val eyeColour: String? = null,
    @SerialName("hairColour")
    val hairColour: String? = null,
    @SerialName("wand")
    val wand: WandResponse? = null,
    @SerialName("patronus")
    val patronus: String? = null,
    @SerialName("hogwartsStudent")
    val hogwartsStudent: String? = null,
    @SerialName("hogwartsStaff")
    val hogwartsStaff: String? = null,
    @SerialName("actor")
    val actor: String? = null,
    @SerialName("alternate_actors")
    val alternate_actors: List<String>? = null,
    @SerialName("alive")
    val alive: String? = null,
    @SerialName("image")
    val image: String? = null,
) {
    fun toCharacterData(): Character? {
        return if (id != null) {
            Character(
                id = id,
                name = name,
                actorName = actor,
                species = species,
                gender = gender,
                house = house,
                dateOfBirth = dateOfBirth,
                patronus = patronus,
                alive = alive,
                imageUrl = image
            )
        } else {
            null
        }
    }
}

@Serializable
data class WandResponse(
    @SerialName("wood")
    val wood: String? = null,
    @SerialName("core")
    val core: String? = null,
    @SerialName("length")
    val length: String? = null,
)
