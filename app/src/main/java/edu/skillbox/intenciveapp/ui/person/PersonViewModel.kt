package edu.skillbox.intenciveapp.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.skillbox.intenciveapp.Api
import edu.skillbox.intenciveapp.Person
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PersonViewModel : ViewModel() {
    private val api = Api()

    val person = MutableStateFlow<Person?>(null)

    fun loadPerson(personId:Long){
        viewModelScope.launch {
            person.value = api.loadPerson(personId)
        }
    }
}