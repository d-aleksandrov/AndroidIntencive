package edu.skillbox.intenciveapp.ui.personlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.skillbox.intenciveapp.Api
import edu.skillbox.intenciveapp.Person
import edu.skillbox.intenciveapp.PersonPage
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class PersonListViewModel : ViewModel() {
    private val api = Api()

    val startPage:String = "https://rickandmortyapi.com/api/character"
    suspend fun loadPersonPage(url:String):PersonPage = api.loadPage(url)
}