package com.company.kokoiti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.kokoiti.databinding.ActivityYourTicketsBinding
import com.google.firebase.database.FirebaseDatabase

class YourTickets : AppCompatActivity() {
    lateinit var binding: ActivityYourTicketsBinding
    var ListAdapter:TicketAdapter?=null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYourTicketsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ticketBottomMenu.selectedItemId = R.id.ticket
        binding.ticketBottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val home = Intent(this, MainActivity::class.java)
                    startActivity(home)
                }
                R.id.admin->{
                    val admin = Intent(this, RegAvtorization::class.java)
                    startActivity(admin)
                }
            }

            true
        }

        binding.recTickets.layoutManager= LinearLayoutManager(this)
        ListAdapter = TicketAdapter()
        binding.recTickets.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData())
    }
    fun getData():ArrayList<ticketModel>{



        val List=ArrayList<ticketModel>()
        database.getReference("Ticket").child(userModel.currentuser?.phone.toString()).get().addOnSuccessListener {
            for (i in it.children){
                var ticket=i.getValue(ticketModel::class.java)
                if(ticket!=null){
                    List.add(ticket)
                    ListAdapter?.loadListToAdapter(List)
                }

            }
        }
        return List
    }

    override fun onStart() {
        super.onStart()
        ListAdapter?.loadListToAdapter(getData())
    }
}