package com.egeuzma.proje.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.BlurMaskFilter
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egeuzma.proje.Controller.UrunDetayi
import com.egeuzma.proje.R
import com.egeuzma.proje.model.Database


class MalzemeAdapter (private val productName : ArrayList<String>,private val productNumber : ArrayList<String>,private val productNote : ArrayList<String>,private val liste:String,private val productCheck : ArrayList<Boolean>):RecyclerView.Adapter<MalzemeAdapter.MalzemeHolder>(){
    class MalzemeHolder(view: View):RecyclerView.ViewHolder(view){
        var recyclerText : TextView? = null

        init {
            recyclerText = view.findViewById(R.id.recyclerViewText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MalzemeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =inflater.inflate(R.layout.recycler_view_row,parent,false)
        return MalzemeHolder(view)
    }

    override fun getItemCount(): Int {
        return productName.size
    }
    /*
    Burada ürüne bir sefer tıklanırsa ürünün üstüne çizgi çeker ve yazıyı blurlaştırır.
    Bu da bu ürünün alındığı anlamına gelir. Ve databasede o ürün alındığı bilgisini günceller.
    Bir ürüne uzun süre basılı tutarsa o ürünün detay sayfasına gider.

    */
    override fun onBindViewHolder(holder: MalzemeHolder, position: Int) {
        var database=Database()
        holder.recyclerText?.text=productName[position]
        if(!productCheck[position]){
            holder.recyclerText?.paintFlags=Paint.ANTI_ALIAS_FLAG
            holder.recyclerText?.paint!!.setMaskFilter(null)
        }else{
            holder.recyclerText?.paint!!.setMaskFilter(BlurMaskFilter(10F, BlurMaskFilter.Blur.INNER))
            holder.recyclerText?.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
        }
        val map = HashMap<String,Any>()
        map.put("UrunAdi",productName[position])
        map.put("UrunAdeti",productNumber[position])
        map.put("UrunNotu",productNote[position])
       holder.itemView.setOnClickListener {
           if(!productCheck[position]){
              productCheck[position]=true
              holder.recyclerText?.paint!!.setMaskFilter(BlurMaskFilter(10F, BlurMaskFilter.Blur.INNER))
              holder.recyclerText?.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
               map.put("isCheck",productCheck[position])
               database.addCheckedProductToList(holder.recyclerText?.context!!,map,position,liste)
           }else{
               productCheck[position]=false
               holder.recyclerText?.paintFlags=Paint.ANTI_ALIAS_FLAG
               holder.recyclerText?.paint!!.setMaskFilter(null)
               map.put("isCheck",productCheck[position])
               database.addCheckedProductToList(holder.recyclerText?.context!!,map,position,liste)
           }
        }
        holder.itemView.setOnLongClickListener {
            val context=holder.recyclerText?.context
            val intent = Intent( context, UrunDetayi::class.java)
            intent.putExtra("isim",productName[position])
            intent.putExtra("not",productNote[position])
            intent.putExtra("adet",productNumber[position])
            intent.putExtra("liste",liste)
            context?.startActivity(intent)
            (context as Activity).finish()
            true
        }
    }
}