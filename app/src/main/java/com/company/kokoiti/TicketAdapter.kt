package com.company.kokoiti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.kokoiti.databinding.ElementTicketBinding
import com.squareup.picasso.Picasso

class TicketAdapter: RecyclerView.Adapter<TicketAdapter.ViewHolder>() {

    private var ListInAdapter = ArrayList<ticketModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_ticket, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketAdapter.ViewHolder, position: Int) {
        holder.bind(ListInAdapter[position])
    }

    override fun getItemCount(): Int {
        return ListInAdapter.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElementTicketBinding.bind(itemView)
        fun bind(ticket: ticketModel) {
            binding.ticketName.text = ticket.name
            binding.ticketPrice.text = ticket.price
            binding.ticketTime.text = ticket.time
            Picasso.get().load(ticket.link).fit().into(binding.ticketImage)



        }
    }

    fun loadListToAdapter(productList: ArrayList<ticketModel>) {
        ListInAdapter = productList
        notifyDataSetChanged()
    }



    fun deleteItem(i: Int): String? {
        var id = ListInAdapter.get(i).name

        ListInAdapter.removeAt(i)

        notifyDataSetChanged()

        return id
    }
}