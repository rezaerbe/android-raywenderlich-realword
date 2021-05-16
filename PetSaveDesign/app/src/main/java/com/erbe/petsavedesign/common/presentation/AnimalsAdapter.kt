package com.erbe.petsavedesign.common.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erbe.petsavedesign.common.presentation.model.UIAnimal
import com.erbe.petsavedesign.common.utils.setImageWithCrossFade
import com.erbe.petsavedesign.databinding.RecyclerViewAnimalItemBinding

class AnimalsAdapter : ListAdapter<UIAnimal, AnimalsAdapter.AnimalsViewHolder>(
    ITEM_COMPARATOR
) {

    private var animalClickListener: AnimalClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsViewHolder {
        val binding = RecyclerViewAnimalItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return AnimalsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalsViewHolder, position: Int) {
        val item: UIAnimal = getItem(position)

        holder.bind(item)
    }

    inner class AnimalsViewHolder(
        private val binding: RecyclerViewAnimalItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UIAnimal) {
            binding.name.text = item.name
            binding.photo.setImageWithCrossFade(item.photo)

            binding.root.setOnClickListener {
                animalClickListener?.onClick(item.id)
            }
        }
    }

    fun setOnAnimalClickListener(animalClickListener: AnimalClickListener) {
        this.animalClickListener = animalClickListener
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UIAnimal>() {
    override fun areItemsTheSame(oldItem: UIAnimal, newItem: UIAnimal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIAnimal, newItem: UIAnimal): Boolean {
        return oldItem.name == newItem.name && oldItem.photo == newItem.photo
    }
}

interface AnimalClickListener {
    fun onClick(animalId: Long)
}