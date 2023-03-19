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

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var list = mutableListOf<Person>()
        set(value) {
            field = value
        }

    inner class MyViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person, position: Int) {
            binding.apply {
                tvName.text = person.name
                tvSurname.text = person.surname
                item.setOnClickListener {
                    itemClick.invoke(person)
                }
                btnMore.setOnClickListener {
                    moreBtnClick.invoke(person, it, position)
                }
            }
            Log.d("tekseriw", "bind: $adapterPosition")
        }
    }

    private var itemClick: (Person) -> Unit = {}
    fun setOnItemClickListener(block: (Person) -> Unit) {
        itemClick = block
    }

    fun removeItem(person: Person, position: Int) {
        list.remove(person)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun addItem() {
        list.add(Person("Name ${list.size + 1}", "Surname ${list.size + 1}"))
        notifyItemInserted(list.size)
        notifyDataSetChanged()
    }

    private var moreBtnClick: (Person, View, Int) -> Unit = { _, _, _ -> }
    fun setOnMoreBtnClickListener(block: (Person, View, Int) -> Unit) {
        moreBtnClick = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.d("tekseriw", "onCreateViewHolder")
        return MyViewHolder(ItemListBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("tekseriw", "onBindViewHolder: $position")
        holder.bind(list[position], position)
    }
}