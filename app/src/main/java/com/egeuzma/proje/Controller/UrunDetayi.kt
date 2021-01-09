package com.egeuzma.proje.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.egeuzma.proje.R
import com.egeuzma.proje.model.Database
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_urun_detayi.*

class UrunDetayi : AppCompatActivity() {
    private lateinit var  db : FirebaseFirestore
    private  var liste : String? = null
    private  var selectedList : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_urun_detayi)
        db = FirebaseFirestore.getInstance()
        val intent = intent
        selectedList=intent.getStringExtra("isim")
        var adet=intent.getStringExtra("adet")
        var not = intent.getStringExtra("not")
        liste = intent.getStringExtra("liste")
        ürünAditextView.text=selectedList
        ürünAdediTextView.text=adet
        ürünNotuTextView.text=not
    }
    //Ürünün gramaj veya adetini değiştirir.Database kaydetmez kaydetmesi için kaydet butonuna basması gerekir.
    fun addAdet(view: View){
        val alert = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.change_adet,null)
        val textview = view.findViewById<TextView>(R.id.textView22)
        val but = view.findViewById<Button>(R.id.adetKaydetbutton)
        val edittext = view.findViewById<EditText>(R.id.adetGirmeText)
        alert.setView(view)
        val dialog = alert.create()
        dialog.show()
        but.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                ürünAdediTextView.text=edittext.text.toString()
                dialog.cancel()
            }
        })
    }
    //Ürüne not eklemek için kullanılan fonksiyon.Database kaydetmez kaydetmesi için kaydet butonuna basması gerekir.
    fun addNote(view: View){
        val alert = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.change_not,null)
        val textview = view.findViewById<TextView>(R.id.toplamKaloriTextView)
        val but = view.findViewById<Button>(R.id.notKaydetbutton)
        val edittext = view.findViewById<EditText>(R.id.notGirmeText)
        alert.setView(view)
        val dialog = alert.create()
        dialog.show()
        but.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                ürünNotuTextView.text=edittext.text
                dialog.cancel()
            }
        })
    }
    //ürünü listeden siler.
    fun deleteProduct(view: View){
        var database = Database()
        database.deleteProduct(liste!!,ürünAditextView.text.toString())
        val intent = Intent(applicationContext,
            ListeIcerik::class.java)
        intent.putExtra("info","old")
        intent.putExtra("isim",liste)
        startActivity(intent)
        finish()
    }
    //ürünün yeni özelliklerini databasede günceller(Gram , Not)
    fun kaydet(view: View) {
        var database = Database()
        database.saveProduct(liste!!,ürünAditextView.text.toString(),ürünAdediTextView.text.toString(),ürünNotuTextView.text.toString())
        val intent = Intent(applicationContext,
            ListeIcerik::class.java)
        intent.putExtra("info","old")
        intent.putExtra("isim",liste)
        startActivity(intent)
        finish()
    }
}