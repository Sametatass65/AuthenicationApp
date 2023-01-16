package com.smt.authenicationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.smt.authenicationapp.databinding.ActivityMainBinding

class ProfilActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    var database : FirebaseDatabase? = null
    var databaseReference : DatabaseReference?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        auth.currentUser?.let { user ->
            val currencyUserEmail = user.email
            binding.profilActivityEmailTextview.text = currencyUserEmail
            val userReference = databaseReference?.child(user.uid)
            userReference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.profilActivityAdSoyadTextview.text =
                        snapshot.child("ad ve soyad").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


            binding.profilActivityCikisYapButton.setOnClickListener {
                auth.signOut()
                val intent = Intent(this, GirisActivity::class.java)
                startActivity(intent)
                finish()
            }
            binding.profilActivityHesabimiSilButton2.setOnClickListener {

                user.delete().addOnCompleteListener { task->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Hesabınız Silindi", Toast.LENGTH_SHORT).show()
                        auth.signOut()
                        val intent = Intent(this,GirisActivity::class.java)
                        startActivity(intent)
                        finish()
                        val currencyUserDb = databaseReference?.child(user.uid)
                        currencyUserDb?.removeValue()
                        Toast.makeText(this, "Adi Soyadi Silindi", Toast.LENGTH_SHORT).show()

                    }
                }.addOnFailureListener { exception->
                    Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
            binding.profilActivityBilgilerimiGuncelleButton.setOnClickListener {
                val intent = Intent(this,GuncellemeActivity::class.java)
                startActivity(intent)
            }
        }




    }
}