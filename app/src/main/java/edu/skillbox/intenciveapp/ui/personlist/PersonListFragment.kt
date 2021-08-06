package edu.skillbox.intenciveapp.ui.personlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import edu.skillbox.intenciveapp.Person
import edu.skillbox.intenciveapp.R
import edu.skillbox.intenciveapp.ui.person.PersonFragment

class PersonListFragment : Fragment() {

    companion object {
        fun newInstance() = PersonListFragment()
    }

    private val viewModel by lazy { ViewModelProvider(this).get(PersonListViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                PersonList(viewModel = viewModel, onPersonClick = ::showPersonInfo)
            }
        }
    }

    private fun showPersonInfo(person: Person) {
        val fragment = PersonFragment.newInstance(personId = person.id)
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .commitNow()
    }
}

@Composable
fun PersonList(
    viewModel: PersonListViewModel,
    onPersonClick: (Person) -> Unit,
) {
    val pagedData = object : PagingSource<String, Person>() {
        override fun getRefreshKey(state: PagingState<String, Person>): String = viewModel.startPage

        override suspend fun load(params: LoadParams<String>): LoadResult<String, Person> {
            val pageInfo = viewModel.loadPersonPage(params.key ?: viewModel.startPage)
            return LoadResult.Page(
                data = pageInfo.results,
                nextKey = pageInfo.info.next,
                prevKey = pageInfo.info.prev,
            )
        }
    }

    val pager = Pager(PagingConfig(pageSize = 20)) { pagedData }.flow.collectAsLazyPagingItems()

    LazyColumn {
        items(pager) {
            it?.let { Person(person = it, onPersonClick = onPersonClick) }
        }
    }
}

@Composable
fun Person(
    person: Person,
    onPersonClick: (Person) -> Unit,
) {
    Row(modifier = Modifier
        .padding(6.dp)
        .clickable { onPersonClick(person) }) {
        Image(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp),
            painter = rememberImagePainter(data = person.image),
            contentDescription = null,
        )
        Column(modifier = Modifier.padding(horizontal = 6.dp)) {
            Text(text = person.name)
            Text(text = person.gender.name)
        }

    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun PersonSample() {
    Person(person = Person(
        id = 3,
        name = "Hello",
        status = "World",
        gender = Person.Gender.Male,
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    ), onPersonClick = {})
}