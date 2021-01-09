package com.egeuzma.proje.Controller

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.egeuzma.proje.MyCallBack
import com.egeuzma.proje.R
import com.egeuzma.proje.adapter.UrunAdapter
import com.egeuzma.proje.model.Database
import com.egeuzma.proje.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_urun_ekleme.*
import java.util.*
import kotlin.collections.ArrayList


class UrunEkleme : AppCompatActivity() {
    private lateinit var  db : FirebaseFirestore
    var productsName : ArrayList<String> = ArrayList()
    var product : ArrayList<Product> = ArrayList()
    var queryTextproducts :ArrayList<String> = ArrayList()
    var adapter : UrunAdapter?=null
    var isim: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_urun_ekleme)
        db = FirebaseFirestore.getInstance()
        val intent = intent
        isim= intent.getStringExtra("isim")
        getProducts()
        //ekranda klayve gibi şeyler açıksa recyclerviewa basılınca kapanır
        recyclerViewProduct.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
               var imm =  getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v!!.getWindowToken(), 0);

                return false;
            }
        });
        //searchbarda dinamik bir arama yapar.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.isNotEmpty()){
                    productsName.clear()
                    val search = newText.toLowerCase(Locale.getDefault())
                    queryTextproducts.forEach {
                        if(it.toLowerCase(Locale.getDefault()).contains(search)){
                            productsName.add(it)
                        }
                        recyclerViewProduct.adapter!!.notifyDataSetChanged()
                    }
                }else{
                    productsName.clear()
                    productsName.addAll(queryTextproducts)
                    recyclerViewProduct.adapter!!.notifyDataSetChanged()
                }
                return true
            }
        })
    }
    //menuyü gösterir
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.category,menu)
        return super.onCreateOptionsMenu(menu)
    }
    //Menüden bir kategori seçildiğinde searchbarı yok eder ve seçilen kategorinin ismini gösterir.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       if(item.title.toString() == "Tüm Ürünler" || item.title.toString() == "All Products"){
           kategoriTextView.visibility=View.INVISIBLE
           searchView.visibility=View.VISIBLE
           getProducts()
       }else{
           kategoriTextView.visibility=View.VISIBLE
           kategoriTextView.text=item.title
           searchView.visibility=View.INVISIBLE
           getCategory(item.title.toString())
       }
        return super.onOptionsItemSelected(item)
    }
    //Uygulamanın diline göre seçilen kategoride bulunan ürünleri listeler.
    fun getCategory(kategori:String) {
        var language = Locale.getDefault().getLanguage()
        if(language=="tr"){
            db.collection("Urunler").whereEqualTo("Category", kategori).addSnapshotListener { snapshot, exception ->
                if (snapshot != null) {
                    if (!snapshot.isEmpty) {
                        productsName.clear()
                        val documents = snapshot.documents
                        for (document in documents) {
                            val isim = document.get("Name") as String
                            productsName.add(isim)
                            adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }
        }else{
            db.collection("Urunler").whereEqualTo("Category_en", kategori).addSnapshotListener { snapshot, exception ->
                if (snapshot != null) {
                    if (!snapshot.isEmpty) {
                        productsName.clear()
                        val documents = snapshot.documents
                        for (document in documents) {
                            val isim = document.get("Name_en") as String
                            productsName.add(isim)
                            adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

    }
    //Databaseden bütün ürünleri çeker ve ekrana gösterir.
    //SearchViewda dinamik arama yapmak için ürün isimlerini querytextproductsda saklar.
    fun getProducts(){
        var database = Database()
        database.getProducts(object : MyCallBack {
            override fun onCallback(value: ArrayList<Any>) {
                productsName.clear()
                queryTextproducts.clear()
                product = value as ArrayList<Product>
                for (ürün in product) {
                    queryTextproducts.add(ürün.isim!!)
                    productsName.add(ürün.isim!!)
                }
                productsName.sort()
                var layoutManager = LinearLayoutManager(this@UrunEkleme)
                recyclerViewProduct.layoutManager = layoutManager
                adapter = UrunAdapter(productsName, isim!!)
                recyclerViewProduct.adapter = adapter
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

}