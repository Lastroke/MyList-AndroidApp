package com.egeuzma.proje.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egeuzma.proje.model.YemekTarif
import com.egeuzma.proje.Controller.YemekIcerik
import com.egeuzma.proje.R

class TarifAdapter (private val tarifname :ArrayList<YemekTarif>): RecyclerView.Adapter<TarifAdapter.TarifHolder>() {

  class TarifHolder(view : View) : RecyclerView.ViewHolder(view){
      var recyclerText : TextView? = null

      init {
          recyclerText = view.findViewById(R.id.recyclerViewText)
      }
  }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarifHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =inflater.inflate(R.layout.recycler_view_row,parent,false)
        return TarifHolder(view)
    }

    override fun getItemCount(): Int {
        return tarifname.size
    }
    //Databasedeki listeleri Recyclerviewe atar böylelikle ekranda gözükür.
    override fun onBindViewHolder(holder: TarifHolder, position: Int) {
        holder.recyclerText?.text=tarifname[position].isim
        holder.itemView.setOnClickListener {
            val context=holder.recyclerText?.context
            val intent = Intent( context, YemekIcerik::class.java)
            intent.putExtra("isim",tarifname[position])
            context?.startActivity(intent)
        }
    }
}