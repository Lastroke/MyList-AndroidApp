package com.egeuzma.proje.Controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.egeuzma.proje.adapter.MalzemeAdapter
import com.egeuzma.proje.MyCallBack
import com.egeuzma.proje.R
import com.egeuzma.proje.model.Database
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_liste_icerik.*

class ListeIcerik : AppCompatActivity() {
    private lateinit var  db : FirebaseFirestore
    var productName : ArrayList<String> = ArrayList()
    var productCheck : ArrayList<Boolean> = ArrayList()
    var adapter : MalzemeAdapter?=null
    var selectedList:String? = null
    var productNumber : ArrayList<String> = ArrayList()
    var productNote : ArrayList<String> = ArrayList()
    var layoutManager: LinearLayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_icerik)
        db = FirebaseFirestore.getInstance()
        val intent = intent
        val info= intent.getStringExtra("info")
        if(info == "new" || info == "old") {
            selectedList = intent.getStringExtra("isim")
            listeNametextView.text = selectedList
            getSelectedListsProduct(selectedList!!)
        }
        //ekranda klavye gibi şeyler açıksa recyclerviewa tıklanırsa klavye gibi şeyleri kapatır.
        listeIcerikrecyclerView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                var imm =  getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v!!.getWindowToken(), 0);

                return false;
            }
        })
    }
    // Seçilen listenin içindeki ürünler databaseden çeker ve ekrana basar.
    fun getSelectedListsProduct(selectedList :String){
        var database=Database()
        database.getSelectedListProducts(object :MyCallBack{
            override fun onCallback(value: ArrayList<Any>) {
            }
            override fun onCallback(
                name: ArrayList<String>,
                number: ArrayList<String>,
                not: ArrayList<String>,
                check: ArrayList<Boolean>
            ) {
                productName=name
                productNumber=number
                productNote=not
                productCheck=check
                checkedProduct(productCheck)

                layoutManager = LinearLayoutManager(this@ListeIcerik)
                listeIcerikrecyclerView.layoutManager = layoutManager
                adapter = MalzemeAdapter(
                    productName,
                    productNumber,
                    productNote,
                    selectedList!!,
                    productCheck
                )
                listeIcerikrecyclerView.adapter = adapter
            }

        },selectedList)
    }
    //Kaç tane ürünün alınıp alınmadığını ölçer ve alınan ürün sayısını günceller.
    fun checkedProduct(list: ArrayList<Boolean>){
        var count=0
        for (product in list){
            if (product){
                count++
            }
        }
        alinanUrunSayisitextView.text=count.toString()+"/"+list.size.toString()
    }
    //Ürün ekleme sayfasına gider.
    fun addProduct(view: View){
        val intent = Intent(applicationContext, UrunEkleme::class.java)
        intent.putExtra("isim",selectedList)
        startActivity(intent)
        finish()
    }
    //Listeyi siler.
    fun deleteList(view: View){
        var list =db.collection("Listeler").document(selectedList!!)
        list.delete()
        val intent = Intent(applicationContext,
            MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}