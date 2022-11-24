package com.company.kokoiti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.kokoiti.databinding.ElementBinding
import com.squareup.picasso.Picasso

class ListAdapter (val clickListener: ClickListener) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var ListInAdapter = ArrayList<KinoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.bind(ListInAdapter[position], clickListener)
    }

    override fun getItemCount(): Int {
        return ListInAdapter.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElementBinding.bind(itemView)
        fun bind(kino: KinoModel, clickListener: ClickListener) {
            binding.elName.text = kino.name
            binding.elPrice.text = kino.price
            binding.elTime.text = kino.time
            Picasso.get().load(kino.link).fit().into(binding.elImage)

            itemView.setOnClickListener {

                clickListener.onClick(kino)
            }

        }
    }

    fun loadListToAdapter(productList: ArrayList<KinoModel>) {
        ListInAdapter = productList
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onClick(kino: KinoModel) {

        }
    }

    fun deleteItem(i: Int): String? {
        var id = ListInAdapter.get(i).name

        ListInAdapter.removeAt(i)

        notifyDataSetChanged()

        return id
    }
}