package uz.nurlibaydev.recyclerviewexample

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.nurlibaydev.recyclerviewexample.databinding.ItemListBinding

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 23:53
 */

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    var list = mutableListOf<Person>()
        set(value) {
            field = value
        }

    inner class ViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.apply {
                tvName.text = person.name
                tvSurname.text = person.surname
                item.setOnClickListener {
                    itemClick.invoke(person)
                }
                btnMore.setOnClickListener {
                    moreBtnClick.invoke(person, it)
                }
            }
            Log.d("tekseriw", "bind: $adapterPosition")
        }
    }

    private var itemClick: (Person) -> Unit = {}
    fun setOnItemClickListener(block: (Person) -> Unit){
        itemClick = block
    }

    private var moreBtnClick: (Person, View) -> Unit = {_, _ ->}
    fun setOnMoreBtnClickListener(block: (Person, View) -> Unit){
        moreBtnClick = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("tekseriw", "onCreateViewHolder")
        return ViewHolder(ItemListBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("tekseriw", "onBindViewHolder: $position")
        holder.bind(list[position])
    }
}