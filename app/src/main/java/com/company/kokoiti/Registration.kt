package com.company.kokoiti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.kokoiti.databinding.ActivityRegistrationBinding
import com.google.firebase.database.*

class Registration : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regReg.setOnClickListener {

            val phone =binding.regPhone.text.toString()
            val pass = binding.regPass.text.toString()

            try {
                if(binding.regCode.text.isEmpty()){
                    val user= userModel(phone,pass,"User")
                    database.child("User").addListenerForSingleValueEvent(object:
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (!snapshot.child(binding.regPhone.text.toString()).exists()) {
                                database.child("User").child(binding.regPhone.text.toString()).setValue(user)
                                Toast.makeText(this@Registration, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                                finish()

                            } else {
                                Toast.makeText(this@Registration, "Пользователь с такими данными уже есть", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
                else{
                    if(binding.regCode.text.toString().toInt()==123){
                        val user= userModel(phone,pass,"Admin")
                        database.child("User").addListenerForSingleValueEvent(object:
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (!snapshot.child(binding.regPhone.text.toString()).exists()) {
                                    database.child("User").child(binding.regPhone.text.toString()).setValue(user)
                                    Toast.makeText(this@Registration, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                                    finish()

                                } else {
                                    Toast.makeText(this@Registration, "Пользователь с такими данными уже есть", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {

                            }

                        })
                    }

                }





            }
            catch (ex: Exception){
                Toast.makeText(this,"Телефон и код цифрами!!", Toast.LENGTH_SHORT).show()
            }



        }
    }
}