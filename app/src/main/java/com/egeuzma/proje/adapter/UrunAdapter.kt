package com.egeuzma.proje.adapter

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egeuzma.proje.R
import com.egeuzma.proje.model.Database
import kotlinx.android.synthetic.main.urun_ekleme_dialog.*

class UrunAdapter (private val product :ArrayList<String>,private val isim:String): RecyclerView.Adapter<UrunAdapter.UrunHolder>() {


    class UrunHolder(view: View) : RecyclerView.ViewHolder(view) {
        var recyclerText: TextView? = null

        init {
            recyclerText = view.findViewById(R.id.recyclerViewText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrunHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_row, parent, false)
        return UrunHolder(view)
    }

    override fun getItemCount(): Int {
        return product.count()
    }
    //ürün ekleme sayfasında bir ürüne tıkladıktan sonra bir pencere oluşturur ve orada ürünün adedini
    //ve notunu girmesini ister ama zorunlu değildir.Kaydet butonuna bastıktan sonra ürünü listeye ekler.
    override fun onBindViewHolder(holder: UrunHolder, position: Int) {
        holder.recyclerText?.text = product[position]
        var database=Database()
        holder.itemView.setOnClickListener {
            var dialog = Dialog(holder.recyclerText?.context!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.urun_ekleme_dialog)
            dialog.ürünEklemeKaydetbutton.setOnClickListener {
                val map = HashMap<String,Any>()
                map.put("UrunAdi",product[position])
                map.put("UrunAdeti",dialog.ürünEklemeAdetGirmeText.text.toString())
                map.put("UrunNotu",dialog.ürünEklemeNotGirmeText.text.toString())
                map.put("isCheck",false)
                database.addProductToList(holder.recyclerText?.context!!,map,isim)
                dialog.cancel()
            }
            dialog.show()

        }
    }
}