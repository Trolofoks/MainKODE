package com.honey.mainkode.adapter

import android.icu.text.DateFormatSymbols
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.honey.mainkode.R
import com.honey.mainkode.databinding.PeopleItemBinding
import com.honey.mainkode.extension.stringRes
import com.honey.mainkode.model.People
import com.honey.mainkode.model.SortBy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate
import java.util.*

class PeoplesAdapter(val listener: Listener?):ListAdapter<People, PeoplesAdapter.ViewHolder>(Comparator()) {

    private var sortedByDob = false

    class ViewHolder(view: View, val listener: Listener?): RecyclerView.ViewHolder(view){
        private fun formatDate(dateString: String): String {
            val date = LocalDate.parse(dateString)
            val month = date.monthValue
            val monthName = DateFormatSymbols(Locale("ru")).months[month - 1].substring(0..2)
            return "${date.dayOfMonth} $monthName"
        }
        private var itemData : People? = null
        init {
            itemView.setOnClickListener {
                itemData?.let { listener?.onClickPeople(it) }
            }
        }
        private val binding = PeopleItemBinding.bind(view)
        fun bind(people: People, sortedByDob: Boolean, previousPerson: People?){
            itemData = people
            binding.apply {
                textName.text = people.firstName + " " + people.lastName

                people.department.stringRes()?.let { textDepartament.setText(it) }
                textTag.text = people.userTag.lowercase()
                if (people.avatarURL.isNotEmpty()) Picasso.get().load(people.avatarURL).into(imageView)
                if (sortedByDob) {
                    textDate.text = formatDate(people.dob)
                    previousPerson?.let {
                        if ((LocalDate.parse(it.dob).month > LocalDate.parse(people.dob).month)){
                            layoutNextYear.visibility = View.VISIBLE
                            textNextYear.text = (LocalDate.now().year + 1).toString()
                        }
                    }
                } else {
                    textDate.text = ""
                    layoutNextYear.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.people_item, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val previousPerson = if (position > 0) getItem(position - 1) else null
        holder.bind(getItem(position),sortedByDob, previousPerson)

    }

    fun dobSort(sortByDob : SortBy){
        sortedByDob = (sortByDob == SortBy.Dob)
        notifyDataSetChanged()
    }

}

