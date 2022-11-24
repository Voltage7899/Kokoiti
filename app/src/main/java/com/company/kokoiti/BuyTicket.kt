package com.company.kokoiti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.kokoiti.databinding.ActivityBuyTicketBinding
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class BuyTicket : AppCompatActivity() {
    lateinit var binding: ActivityBuyTicketBinding
    private var kino:KinoModel?=null
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBuyTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name= intent.getStringExtra("kinoName").toString()
        val time= intent.getStringExtra("kinoTime").toString()

        getKino(name,time)

        binding.buyBuy.setOnClickListener {

            val id = userModel.currentuser?.phone.toString()
            val ticket=ticketModel(id,kino?.name,kino?.price,kino?.time,kino?.link)
            database.child("Ticket").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(id).child(kino?.name.toString()).exists()){
                        Toast.makeText(this@BuyTicket,"У вас уже куплен билет", Toast.LENGTH_LONG).show()
                    }
                    else{
                        database.child("Ticket").child(id).child(kino?.name.toString()).setValue(ticket)
                        Toast.makeText(this@BuyTicket,"Вы купили билет", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }
    fun getKino(name:String,time:String){

        database.child("Kino").child(name).child(time).get().addOnSuccessListener {
            kino=it.getValue(KinoModel::class.java)

            Picasso.get().load(kino?.link).fit().into(binding.descImage)

            binding.descName.setText(kino?.name)
            binding.descDesc.setText(kino?.description)
            binding.descTime.setText(kino?.time)
            binding.descPrice.setText(kino?.price)
        }
    }

}