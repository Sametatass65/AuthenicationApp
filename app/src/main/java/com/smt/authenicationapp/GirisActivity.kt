package com.smt.authenicationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.smt.authenicationapp.databinding.ActivityGirisBinding

class GirisActivity : AppCompatActivity() {
    lateinit var binding :ActivityGirisBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val currencyUser = auth.currentUser

        if(currencyUser!=null){
            val intent = Intent(this,ProfilActivity :: class.java)
            startActivity(intent)
            finish()
        }
        binding.girisActivityGirisYapButton.setOnClickListener {
            val email = binding.girisActivityEmailTextview.text.toString()
            val password = binding.girisActivityPasswordTextview.text.toString()

            if (email.isEmpty()){
                binding.girisActivityEmailTextview.error = "Lütfen e-mail adresinizi yazınız"
                return@setOnClickListener
            }else if(password.isEmpty()){
                binding.girisActivityPasswordTextview.error = "Lütfen e-mail adresinizi yazınız"
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        val intent =Intent(this,ProfilActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener { exception->
                    Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }

        binding.girisActivityYeniUyeTextView.setOnClickListener {
            val intent = Intent(this,KayitActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.girisActivityParolamiUnuttumTextview.setOnClickListener {
            val intent = Intent(this,UnuttumActivity::class.java)
            startActivity(intent)
        }
    }
}


































