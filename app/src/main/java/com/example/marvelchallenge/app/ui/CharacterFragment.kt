package com.example.marvelchallenge.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.marvelchallenge.R
import com.example.marvelchallenge.app.vm.CharacterViewModel
import com.example.marvelchallenge.databinding.FragmentCharacterBinding
import com.example.marvelchallenge.utils.SQUARE_FANTASTIC
import com.example.marvelchallenge.utils.loadFromUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.setNavigationIcon(R.drawable.ic_close)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by viewModels<CharacterViewModel>()

        viewModel.setCharacterId(checkNotNull(requireArguments().getInt(EXTRA_CHARACTER_ID)))

        val comicLayoutList = listOf(
            binding.lytComic1,
            binding.lytComic2,
            binding.lytComic3,
            binding.lytComic4
        )

        viewModel.character.observe(viewLifecycleOwner) { character ->
            with(character) {
                binding.toolbarTitle.text = name
                binding.imgCharacter.loadFromUrl(SQUARE_FANTASTIC, thumbnail)

                binding.txtDescription.isVisible = description.isNotBlank()
                binding.txtDescription.text = description

                binding.txtComicsTitle.isVisible = comics?.items?.isNotEmpty() == true

                comics?.items?.take(4)?.forEachIndexed { index, comicSummary ->
                    val name = comicSummary.name
                    comicLayoutList[index].apply {
                        visibility = View.VISIBLE
                        findViewById<TextView>(R.id.txt_comic_title).text = name
                        findViewById<TextView>(R.id.txt_year).text =
                            name.substring(name.indexOf("(") + 1, name.indexOf(")"))
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_CHARACTER_ID = "character_id"
    }
}
