package com.smt.authenicationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.smt.authenicationapp.databinding.ActivityUnuttumBinding

class UnuttumActivity : AppCompatActivity() {
    lateinit var binding: ActivityUnuttumBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUnuttumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.unutumActivityBilgilendirmeTextview.visibility = View.INVISIBLE


        binding.unutumActivityParolaSifirlaButton.setOnClickListener {
            val psifirlamaEmail = binding.unuttumActivityEmailTextview.text.toString().trim()
            if (TextUtils.isEmpty(psifirlamaEmail)){
                binding.unuttumActivityEmailTextview.error = "Lütfen email adresinizi giriniz"
            }else{
                auth.sendPasswordResetEmail(psifirlamaEmail)
                    .addOnCompleteListener{ task->
                        if (task.isSuccessful){
                            binding.unutumActivityBilgilendirmeTextview.visibility = View.VISIBLE
                        }
                    }.addOnFailureListener{exception->
                        Toast.makeText(this, "Girdiğiniz Gmail ile ilgili bir kayıt bulunamadı!!", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        binding.unutumActivityGirisYapButton.setOnClickListener {
            val intent = Intent(this,GirisActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}





































