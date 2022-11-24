package com.company.kokoiti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.company.kokoiti.databinding.ActivityWorkZoneBinding
import com.google.firebase.database.FirebaseDatabase

class WorkZone : AppCompatActivity(),ListAdapter.ClickListener {
    lateinit var binding: ActivityWorkZoneBinding
    var ListAdapter:ListAdapter?=null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkZoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.workZoneBottomMenu.selectedItemId = R.id.admin
        binding.workZoneBottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val admin = Intent(this, MainActivity::class.java)
                    startActivity(admin)
                }
                R.id.ticket->{
                    val admin = Intent(this, YourTickets::class.java)
                    startActivity(admin)
                }
            }

            true
        }

        binding.addMode.setOnClickListener {
            startActivity(Intent(this,Add::class.java))
        }
        binding.recAdmin.layoutManager= GridLayoutManager(this,2)
        ListAdapter = ListAdapter(this)
        binding.recAdmin.adapter=ListAdapter
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
    override fun onStart() {
        super.onStart()
        ListAdapter?.loadListToAdapter(getData())
    }
}