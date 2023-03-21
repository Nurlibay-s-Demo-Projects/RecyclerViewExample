package uz.nurlibaydev.recyclerviewexample

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.nurlibaydev.recyclerviewexample.databinding.ItemAdsBinding
import uz.nurlibaydev.recyclerviewexample.databinding.ItemListBinding

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 23:53
 */

class MyAdapter : RecyclerView.Adapter<ViewHolder>() {

    var list = mutableListOf<Any>()
        set(value) {
            field = value
        }

    companion object {
        const val VIEW_TYPE_PERSON = 1
        const val VIEW_TYPE_AD = 0
    }

    inner class ViewHolderPerson(private val binding: ItemListBinding) : ViewHolder(binding.root) {
        fun bind(person: Person) {
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

    inner class ViewHolderAd(private val binding: ItemAdsBinding) : ViewHolder(binding.root) {
        fun bind(ads: Ads) {
            binding.tvAdTitle.text = ads.title
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_PERSON -> ViewHolderPerson(ItemListBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)))
            VIEW_TYPE_AD -> ViewHolderAd(ItemAdsBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_ads, parent, false)))
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderPerson -> {
                holder.bind(list[position] as Person)
            }
            is ViewHolderAd -> {
                holder.bind(list[position] as Ads)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is Person -> VIEW_TYPE_PERSON
            is Ads -> VIEW_TYPE_AD
            else -> 0
        }
    }
}