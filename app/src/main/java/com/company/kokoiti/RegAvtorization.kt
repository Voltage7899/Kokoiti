package com.company.kokoiti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.kokoiti.databinding.ActivityRegAvtorizationBinding
import com.google.firebase.database.*
import com.google.firebase.database.R

class RegAvtorization : AppCompatActivity() {
    lateinit var binding: ActivityRegAvtorizationBinding
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegAvtorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signRegBottomMenu.selectedItemId=com.company.kokoiti.R.id.admin

        binding.signRegBottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                com.company.kokoiti.R.id.home -> {
                    val admin = Intent(this, MainActivity::class.java)
                    startActivity(admin)
                }
                com.company.kokoiti.R.id.ticket->{
                    val admin = Intent(this, YourTickets::class.java)
                    startActivity(admin)
                }
            }

            true
        }
        binding.register.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
        }
        binding.sing.setOnClickListener {
            database.child("User").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child(binding.phone.text.toString()).exists()) {
                        val userCurrentData: userModel? = snapshot.child(binding.phone.text.toString()).getValue(userModel::class.java)



                        if (userCurrentData?.phone.equals(binding.phone.text.toString()) && userCurrentData?.pass.equals(
                                binding.password.text.toString()) && userCurrentData?.status=="User")
                        {
                            Toast.makeText(this@RegAvtorization, "Вы вошли как юзер", Toast.LENGTH_SHORT).show()
                            userModel.currentuser=userCurrentData
                            val intent = Intent(this@RegAvtorization, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else if(userCurrentData?.phone.equals(binding.phone.text.toString()) && userCurrentData?.pass.equals(
                                binding.password.text.toString()) && userCurrentData?.status=="Admin"){
                            Toast.makeText(this@RegAvtorization, "Вы вошли как админ", Toast.LENGTH_SHORT).show()
                            userModel.currentuser=userCurrentData
                            val intent = Intent(this@RegAvtorization, WorkZone::class.java)
                            startActivity(intent)

                        }
                        else {
                            Toast.makeText(this@RegAvtorization, "Неверные данные", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this@RegAvtorization, "Номера не существует", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        }
    }
}