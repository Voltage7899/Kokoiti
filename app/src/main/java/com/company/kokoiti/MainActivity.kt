package com.company.kokoiti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.company.kokoiti.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(),ListAdapter.ClickListener {
    lateinit var binding: ActivityMainBinding
    var ListAdapter:ListAdapter?=null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.admin -> {
                    val admin = Intent(this, RegAvtorization::class.java)
                    startActivity(admin)
                }
                R.id.ticket->{
                    val admin = Intent(this, YourTickets::class.java)
                    startActivity(admin)
                }
            }

            true
        }

        binding.recUser.layoutManager= GridLayoutManager(this,2)
        ListAdapter = ListAdapter(this)
        binding.recUser.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData())
    }
    fun getData():ArrayList<KinoModel>{



        val List=ArrayList<KinoModel>()
        database.getReference("Kino").get().addOnSuccessListener {
            for (t in it.children){
                for(item in t.children){
                    var kino=item.getValue(KinoModel::class.java)
                    if(kino!=null){
                        List.add(kino)

                    }
                }


            }
            ListAdapter?.loadListToAdapter(List)
        }
        return List
    }
    override fun onClick(kino: KinoModel) {
        startActivity(Intent(this, BuyTicket::class.java).apply {
            putExtra("kinoName",kino.name)
            putExtra("kinoTime",kino.time)

        })
    }
    override fun onStart() {
        super.onStart()
        ListAdapter?.loadListToAdapter(getData())
    }
}