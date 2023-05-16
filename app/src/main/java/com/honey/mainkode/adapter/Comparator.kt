package com.honey.mainkode.adapter

import androidx.recyclerview.widget.DiffUtil
import com.honey.model.People

class Comparator : DiffUtil.ItemCallback<People>(){
    override fun areItemsTheSame(oldItem: People, newItem: People): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: People, newItem: People): Boolean = oldItem == newItem
}