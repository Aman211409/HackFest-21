package com.hackfest21.covigenix.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.Model.Request.ProviderStatusModel
import com.hackfest21.covigenix.R

class MyRequestsListAdapter(val list: List<ProviderStatusModel>, val listListener: ListListener): RecyclerView.Adapter<MyRequestsListAdapter.ListViewHolder>(){

    class ListViewHolder(itemView: View, val listener: ListListener): RecyclerView.ViewHolder(itemView){
        val name: TextView
        val area: TextView
        val phone: TextView
        val share: Button

        init{
            name = itemView.findViewById(R.id.textViewHospitalsName)
            area = itemView.findViewById(R.id.textViewArea)
            phone = itemView.findViewById(R.id.textViewPhoneNo)
            share = itemView.findViewById(R.id.button)

            share.setOnClickListener { listener.onShare(adapterPosition) }
        }
    }

    interface ListListener{
        fun onShare(pos: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_requestedhospitals, parent, false)
        return ListViewHolder(view, listListener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size
    }
}