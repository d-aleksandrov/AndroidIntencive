package edu.skillbox.intenciveapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Person(
    val id: Long,
    val name: String,
    val status: String,
    val gender: Gender,
    val image: String? = null,
) {
    @Serializable
    enum class Gender {
        Male, Female, Genderless, @SerialName("unknown") Unknown
    }
}
