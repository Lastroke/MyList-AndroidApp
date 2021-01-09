package com.egeuzma.proje.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.egeuzma.proje.MyCallBack
import com.egeuzma.proje.R
import com.egeuzma.proje.adapter.RecyclerAdapter
import com.egeuzma.proje.model.Database
import com.egeuzma.proje.model.Liste
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private lateinit var  db : FirebaseFirestore
    var malzeme : ArrayList<HashMap<String,Any>> = ArrayList()
    var listName : ArrayList<String> = ArrayList()
    var lists :ArrayList<Liste> = ArrayList()
    var adapter : RecyclerAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button2.isEnabled= false
        db = FirebaseFirestore.getInstance()
        getData()
    }
    //Databasedeki oluşturulmuş listeleri alır ve ana sayfada bastırır.
    fun getData(){
        var database = Database()
        database.getListData(object :MyCallBack {
            override fun onCallback(value: ArrayList<Any>) {
                lists.clear()
                lists = value as ArrayList<Liste>
                for (liste in lists) {
                    listName.add(liste.isim!!)
                }
                var layoutManager = LinearLayoutManager(this@MainActivity)
                ListerecyclerView.layoutManager = layoutManager
                adapter = RecyclerAdapter(lists)
                ListerecyclerView.adapter = adapter
            }

            override fun onCallback(
                name: ArrayList<String>,
                number: ArrayList<String>,
                not: ArrayList<String>,
                check: ArrayList<Boolean>
            ) {

            }
        })
    }
    fun goToYemekTarif(view :View){
        val intent = Intent(applicationContext,
            YemekTarifleri::class.java)
        startActivity(intent)
    }
    fun goToKalori(view: View){
        val intent = Intent(applicationContext,
            KaloriHesaplayici::class.java)
        startActivity(intent)
    }
    //butona tıkladıktan sonra pencere açılır kullanıcı liste ismi girer.
    //Öyle bir liste varsa uyarı mesaj verir. Öyle bir liste yoksa database ekler.
   fun createNewList(view: View){
       val alert = AlertDialog.Builder(this)
       val view = layoutInflater.inflate(R.layout.change_liste_ismi,null)
       val textview = view.findViewById<TextView>(R.id.yeniListetextView)
       val but = view.findViewById<Button>(R.id.kaydetbutton)
       val edittext = view.findViewById<EditText>(R.id.yeniListe)
       alert.setView(view)
       val dialog = alert.create()
       dialog.show()
       but.setOnClickListener(object : View.OnClickListener{
           override fun onClick(v: View?) {
               val map = HashMap<String,Any>()
               map.put("isim",edittext.text.toString())
               map.put("Urunler",malzeme)
               if(listName.contains(edittext.text.toString())){
                   textview.text=resources.getString(R.string.toast1)
               }else{
                   db.collection("Listeler").document(edittext.text.toString()).set(map)
                   val intent = Intent(applicationContext,ListeIcerik::class.java)
                   intent.putExtra("info","new")
                   intent.putExtra("isim",edittext.text.toString())
                   startActivity(intent)
                   dialog.cancel()
               }
           }
       })
   }

    override fun onResume() {
        getData()
        super.onResume()
    }
}