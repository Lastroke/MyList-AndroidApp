package com.egeuzma.proje.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.egeuzma.proje.adapter.KaloriUrunAdapter
import com.egeuzma.proje.MyCallBack
import com.egeuzma.proje.R
import com.egeuzma.proje.model.Database
import com.egeuzma.proje.model.Product
import kotlinx.android.synthetic.main.activity_kalori_hesaplayici.*
import java.util.*
import kotlin.collections.ArrayList

var selectedItemsList:ArrayList<String> = ArrayList()
var toplamKalori = 0.0

class KaloriHesaplayici : AppCompatActivity() {

    var productList : ArrayList<Product> = ArrayList()
    var productsName : ArrayList<String> = ArrayList()
    var productsCalorie :ArrayList<Number> = ArrayList()
    var queryTextproducts :ArrayList<String> = ArrayList()
    var calorie1 :ArrayList<Number> = ArrayList()
    var adapter : KaloriUrunAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kalori_hesaplayici)

        verileriFirestoredanCekme()

        var layoutManager = LinearLayoutManager(this)
        searchResultRecyclerView.layoutManager = layoutManager
        adapter = KaloriUrunAdapter(
            productsName,
            productsCalorie,
            this
        )
        searchResultRecyclerView.adapter =adapter
        //temizle butonunu çalıştırır ve refreshproducts fonksiyonunu çağırır seçilen ürünleri temizlemek için.
        val refreshBtn = findViewById<Button>(R.id.calRefresh)
        refreshBtn.setOnClickListener{
            refreshProducts()
            val intent = intent
            finish()
            startActivity(intent)
        }
        //Ana sayfaya döndürür.
        val anasayfabtn = findViewById<Button>(R.id.goHome)
        anasayfabtn.setOnClickListener{
            finish()
            goToHome()
        }
        var itemsLayout : LinearLayout = findViewById(R.id.kalori_item_layout)
        createDisplayItems(itemsLayout)
        toplamKaloriTextView.text=resources.getString(R.string.toplamkalori)+" = "+ toplamKalori
        kaloriSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            //dinamik arama yapar searchbarda
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.isNotEmpty()){
                    productsName.clear()
                    productsCalorie.clear()
                    val search = newText.toLowerCase(Locale.getDefault())
                    queryTextproducts.forEach {
                        if(it.toLowerCase(Locale.getDefault()).contains(search)){
                            productsCalorie.add(calorie1.get(queryTextproducts.indexOf(it)))
                            productsName.add(it)
                        }
                        searchResultRecyclerView.adapter!!.notifyDataSetChanged()
                    }
                }else{
                    productsName.clear()
                    productsName.addAll(queryTextproducts)
                    productsCalorie.clear()
                    productsCalorie.addAll(calorie1)
                    searchResultRecyclerView.adapter!!.notifyDataSetChanged()
                }
                return true
            }
        })
    }


    fun createDisplayItems(itemsLayout:LinearLayout)
    {
        for (str in selectedItemsList){
            createDisplayItem(str?:"",itemsLayout)
        }
    }

     fun createDisplayItem(inputText : String,itemsLayout:LinearLayout)
    {
        val itemRowView = layoutInflater.inflate(R.layout.kalori_items,null)
        val itemRowText: TextView = itemRowView.findViewById(R.id.kal_item_row)
        itemRowText.text = inputText
        itemsLayout.addView(itemRowView)
    }
    //Temizle butonuna tıklayınca seçilen ürünleri siler.
    fun refreshProducts() {
        selectedItemsList.clear()
        toplamKalori =0.0
    }

    fun goToHome()
    {
        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
    }
    //ürünleri databaseden çeker ekrana gösterir. Ürün ismi ve calorisini saklar.
    fun verileriFirestoredanCekme(){
        var database = Database()
        productsName.clear()
        queryTextproducts.clear()
        database.getProducts(object :MyCallBack{
            override fun onCallback(value: ArrayList<Any>) {
                productList=value as ArrayList<Product>
                for(product in productList){
                    var name = product.isim as String
                    var calorie = product.calorie as Number
                    productsName.add(name)
                    productsName.sort()
                    queryTextproducts.add(name)
                    productsCalorie.add(calorie)
                    calorie1.add(calorie)
                    adapter!!.notifyDataSetChanged()
                }
            }
            override fun onCallback(
                name: ArrayList<String>,
                number: ArrayList<String>,
                not: ArrayList<String>,
                check: ArrayList<Boolean>
            ) {
                //bir şey gerek yok
            }

        })
    }
}