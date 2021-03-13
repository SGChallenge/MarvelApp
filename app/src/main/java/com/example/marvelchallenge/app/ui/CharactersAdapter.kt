package com.example.marvelchallenge.app.ui

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelchallenge.R
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.CharacterPaged
import com.example.marvelchallenge.utils.SQUARE_LARGE
import com.example.marvelchallenge.utils.inflate
import com.example.marvelchallenge.utils.loadFromUrl

class CharactersAdapter(private val navController: NavController) :
    PagingDataAdapter<CharacterPaged, CharactersAdapter.ViewHolder>(CharacterDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.list_item_character))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.character?.let { item ->
            holder.bind(item)
            holder.itemView.setOnClickListener {
                navController.navigate(
                    R.id.action_characters_fragment_to_characterFragment,
                    bundleOf(CharacterFragment.EXTRA_CHARACTER_ID to item.id)
                )
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgThumbnail: ImageView = view.findViewById(R.id.img_thumbnail)
        private val txtCharacterName: TextView = view.findViewById(R.id.txt_character_name)
        private val txtCharacterDescription: TextView =
            view.findViewById(R.id.txt_character_description)

        fun bind(character: Character) {
            with(character) {
                imgThumbnail.loadFromUrl(SQUARE_LARGE, thumbnail)
                txtCharacterName.text = name
                txtCharacterDescription.text = description
            }
        }
    }
}

object CharacterDiffCallBack : DiffUtil.ItemCallback<CharacterPaged>() {
    override fun areItemsTheSame(oldItem: CharacterPaged, newItem: CharacterPaged): Boolean =
        oldItem.character.id == newItem.character.id

    override fun areContentsTheSame(oldItem: CharacterPaged, newItem: CharacterPaged): Boolean =
        oldItem == newItem
}
