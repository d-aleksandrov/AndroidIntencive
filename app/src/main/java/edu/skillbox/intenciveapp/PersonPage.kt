package edu.skillbox.intenciveapp

import kotlinx.serialization.Serializable

@Serializable
class PersonPage(
    val info: Info,
    val results: List<Person>,
) {
    @Serializable
    class Info(
        val next: String? = null,
        val prev: String? = null,
    )
}