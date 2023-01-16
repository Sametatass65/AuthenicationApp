package com.smt.authenicationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.smt.authenicationapp.databinding.ActivityGuncellemeBinding
import com.smt.authenicationapp.databinding.ActivityUnuttumBinding
import kotlinx.android.synthetic.main.activity_giris.*

class GuncellemeActivity : AppCompatActivity() {
    lateinit var binding: ActivityGuncellemeBinding
    private lateinit var auth : FirebaseAuth
    var database: FirebaseDatabase?=null
    var databaseReference : DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGuncellemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        val currencyUser = auth.currentUser

        currencyUser?.let { user->
            binding.guncelleActivityEmailTextview.setText(user.email)

            val currencyUserDb = databaseReference?.child(user.uid)
            currencyUserDb?.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   binding.guncelleActivityAdSoyadTextview.setText(snapshot.child("ad ve soyad").value.toString())
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
            binding.guncelleActivityGuncelleButton.setOnClickListener {
                val guncelEmail = binding.guncelleActivityEmailTextview.text.toString().trim()
                user.updateEmail(guncelEmail).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Gmailiniz Güncellendi ", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Güncelleme İşlemi Başarısız ", Toast.LENGTH_SHORT).show()
                    }
                }
                val guncelPassword = binding.guncelleActivityPasswordTextview.text.toString().trim()
                user.updatePassword(guncelPassword).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Şifreniz Güncellendi ", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Güncelleme İşlemi Başarısız ", Toast.LENGTH_SHORT).show()
                    }
                }

                val guncelAd = binding.guncelleActivityAdSoyadTextview.text.toString().trim()
                val currencyUserDb = databaseReference?.child(user.uid)
                currencyUserDb?.removeValue()
                currencyUserDb?.child("ad ve soyad")?.setValue(guncelAd)
                Toast.makeText(this, "Adiniz Güncellendi ", Toast.LENGTH_SHORT).show()
                
            }
            binding.guncelleActivityGirisYapButton.setOnClickListener {
                val intent = Intent(this,GirisActivity::class.java)
                startActivity(intent)
                finish()

            }

        }






    }
}








































