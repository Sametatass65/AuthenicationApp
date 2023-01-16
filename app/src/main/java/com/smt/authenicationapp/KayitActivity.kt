package com.smt.authenicationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.smt.authenicationapp.databinding.ActivityKayitBinding

class KayitActivity : AppCompatActivity() {
    lateinit var binding: ActivityKayitBinding
    private lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference?=null
    var database : FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityKayitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        binding.kayitActivityKaydetYapButton.setOnClickListener {
            val ad_soyad = binding.kayitActivityAdSoyadTextview.text.toString()
            val e_mail = binding.kayitActivityEmailTextview.text.toString()
            val password = binding.kayitActivityPasswordTextview.text.toString()

            if (ad_soyad.isEmpty()) {
                binding.kayitActivityAdSoyadTextview.error = "Lütfen adinizi giriniz!!"
                return@setOnClickListener
            } else if (e_mail.isEmpty()) {
                binding.kayitActivityEmailTextview.error = "Lütfen Email adresinizi giriniz!!"
                return@setOnClickListener
            } else if (password.isEmpty()) {
                binding.kayitActivityPasswordTextview.error = "Lütfen şifreinizi giriniz!!"
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(e_mail, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currencyUser = auth.currentUser

                        val currencyUserDb = currencyUser?.let { id -> databaseReference?.child(id.uid) }
                        currencyUserDb?.let {
                            it.child("ad ve soyad").setValue(ad_soyad)
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()

                }

        }

        binding.kayitActivityGirisYapButton2.setOnClickListener {
            val email = binding.kayitActivityEmailTextview.text.toString()
            val password= binding.kayitActivityPasswordTextview.text.toString()

            if (email.isEmpty()){
                binding.kayitActivityEmailTextview.error ="Lütfen e postanızı giriniz"
                return@setOnClickListener
            }else if(password.isEmpty()){
                binding.kayitActivityPasswordTextview.error ="lütfen şifrenizi giriniz"
            }
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if (task.isSuccessful){
                        val intent = Intent(applicationContext,ProfilActivity :: class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener { exception->
                    Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }

        }
    }
}