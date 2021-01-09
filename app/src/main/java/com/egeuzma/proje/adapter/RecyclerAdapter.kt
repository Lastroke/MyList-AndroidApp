package com.egeuzma.proje.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egeuzma.proje.model.Liste
import com.egeuzma.proje.Controller.ListeIcerik
import com.egeuzma.proje.R

class RecyclerAdapter(private val listname :ArrayList<Liste>): RecyclerView.Adapter<RecyclerAdapter.PostHolder>(){



    class PostHolder(view: View) : RecyclerView.ViewHolder(view){
        var recyclerText : TextView? = null

        init {
            recyclerText = view.findViewById(R.id.recyclerViewText)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =inflater.inflate(R.layout.recycler_view_row,parent,false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return listname.size
    }
    //Databasedeki listeleri recyclerviewa atar ve böylelikle listeler ekranda gözükür.
    //Bir listeye tıklanınca o listenin içerik sayfasına götürür.
    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.recyclerText?.text =listname[position].isim
        holder.itemView.setOnClickListener {
            val context=holder.recyclerText?.context
            val intent = Intent( context, ListeIcerik::class.java)
            intent.putExtra("info","old")
            intent.putExtra("isim",listname[position].isim)
            context?.startActivity(intent)
        }

    }
}