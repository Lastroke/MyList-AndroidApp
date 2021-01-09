package com.egeuzma.proje.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.egeuzma.proje.MyCallBack
import com.egeuzma.proje.R
import com.egeuzma.proje.adapter.TarifAdapter
import com.egeuzma.proje.model.Database
import com.egeuzma.proje.model.YemekTarif
import kotlinx.android.synthetic.main.activity_yemek_tarifleri.*

class YemekTarifleri : AppCompatActivity() {
    var tarifler : ArrayList<YemekTarif> = ArrayList()
    var adapter : TarifAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yemek_tarifleri)
        getData()
    }
    fun getData(){
        var database = Database()
        database.getRecipes(object : MyCallBack {
            override fun onCallback(value: ArrayList<Any>) {
                tarifler.clear()
                tarifler = value as ArrayList<YemekTarif>
                var layoutManager = LinearLayoutManager(this@YemekTarifleri)
                tariflerRecyclerView.layoutManager = layoutManager
                adapter = TarifAdapter(tarifler)
                tariflerRecyclerView.adapter = adapter
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