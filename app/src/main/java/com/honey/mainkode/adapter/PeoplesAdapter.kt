package com.honey.mainkode.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.honey.mainkode.R
import com.honey.mainkode.databinding.PeopleItemBinding
import com.honey.mainkode.model.Department
import com.honey.mainkode.model.People
import com.squareup.picasso.Picasso

class PeoplesAdapter:RecyclerView.Adapter<PeoplesAdapter.ViewHolder>() {
    var peoples = listOf<People>()
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = PeopleItemBinding.bind(view)
        fun bind(people: People){
            binding.apply {
                textName.text = people.firstName + " " + people.lastName
                Department.map[people.department]?.let { textDepartament.setText(it) }
                textTag.text = people.userTag
                Picasso.get().load(people.avatarURL).into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.people_item, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = peoples.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int){holder.bind(peoples[position])}

    fun setList(list: List<People>){
        peoples = list
        notifyDataSetChanged()
        Log.d("MyLog","peoples value : $peoples")
    }
}