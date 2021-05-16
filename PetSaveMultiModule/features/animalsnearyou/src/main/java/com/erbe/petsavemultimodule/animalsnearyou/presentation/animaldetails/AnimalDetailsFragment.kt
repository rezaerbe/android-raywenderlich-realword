package com.erbe.petsavemultimodule.animalsnearyou.presentation.animaldetails

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.erbe.petsavemultimodule.animalsnearyou.R
import com.erbe.petsavemultimodule.animalsnearyou.databinding.FragmentDetailsBinding
import com.erbe.petsavemultimodule.animalsnearyou.presentation.animaldetails.model.UIAnimalDetailed
import com.erbe.common.utils.setImage
import com.erbe.common.utils.toEnglish
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimalDetailsFragment : Fragment() {

    companion object {
        const val ANIMAL_ID = "id"
    }

    private val binding get() = _binding!!
    private var _binding: FragmentDetailsBinding? = null

    private val viewModel: AnimalDetailsFragmentViewModel by viewModels()

    private var animalId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animalId = requireArguments().getLong(ANIMAL_ID)
    }

    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_share, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.share) {
            navigateToSharing()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToSharing() {
        val animalId = requireArguments().getLong(ANIMAL_ID)

        val directions = AnimalDetailsFragmentDirections.actionDetailsToSharing(animalId)

        findNavController().navigate(directions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        val event = AnimalDetailsEvent.LoadAnimalDetails(animalId!!)
        viewModel.handleEvent(event)
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
              is AnimalDetailsViewState.Loading -> {
                displayLoading()
              }
              is AnimalDetailsViewState.Failure -> {
                displayError()
              }
              is AnimalDetailsViewState.AnimalDetails -> {
                displayPetDetails(state.animal)
              }
            }
        }
    }

    private fun displayPetDetails(animalDetails: UIAnimalDetailed) {
        binding.group.isVisible = true
        binding.name.text = animalDetails.name
        binding.description.text = animalDetails.description
        binding.image.setImage(animalDetails.photo)
        binding.sprayedNeutered.text = animalDetails.sprayNeutered.toEnglish()
        binding.specialNeeds.text = animalDetails.specialNeeds.toEnglish()
    }

    private fun displayError() {
        binding.group.isVisible = false
        Snackbar.make(requireView(), R.string.an_error_occurred, Snackbar.LENGTH_SHORT).show()
    }

    private fun displayLoading() {
        binding.group.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}