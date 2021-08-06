package edu.skillbox.intenciveapp.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import edu.skillbox.intenciveapp.Person
import edu.skillbox.intenciveapp.databinding.FragmentPersonBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PersonFragment : Fragment() {

    companion object {
        fun newInstance(personId: Long): PersonFragment {
            val fragment = PersonFragment()
            val args = Bundle()
            args.putLong("KEY", personId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: PersonViewModel
    private lateinit var binding: FragmentPersonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentPersonBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        viewModel.loadPerson(arguments?.getLong("KEY") ?: 42L)
        viewModel.person.onEach { person -> if (person != null) displayPerson(person) }
            .launchIn(lifecycleScope)

        binding.closeButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    private fun displayPerson(person: Person) {
        binding.title.text = person.name
        val description = person.status + "   " + person.gender.name
        binding.description.text = description
        binding.avatar.load(person.image)
    }

}