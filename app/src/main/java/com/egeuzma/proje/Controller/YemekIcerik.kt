package com.egeuzma.proje.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.egeuzma.proje.R
import com.egeuzma.proje.model.Database
import com.egeuzma.proje.model.YemekTarif
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_yemek_icerik.*

class YemekIcerik : AppCompatActivity() {
    private  lateinit var db : FirebaseFirestore
    var selectedTarif : String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yemek_icerik)
        db = FirebaseFirestore.getInstance()
        val intent = intent
        val selectedRecept = intent.getSerializableExtra("isim") as YemekTarif
        selectedTarif = selectedRecept.isim
        var database=Database()
        database.getReceptFromFirebase(selectedTarif!!,tarifText,tarifImageView)
    }
    //Yemek tarifindeki malzemelerden yeni liste oluşturur ve ana sayfaya döner.
    fun addMalzeme(view: View){
        var database = Database()
        database.makeSelectedRecipesToList(selectedTarif!!)
        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}