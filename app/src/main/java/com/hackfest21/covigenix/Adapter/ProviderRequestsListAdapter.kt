package com.hackfest21.covigenix.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.Model.Request.PatientRequestModel
import com.hackfest21.covigenix.R

class ProviderRequestsListAdapter(val list: List<PatientRequestModel>, val listListener: ListListener, val map: HashMap<Int, String>): RecyclerView.Adapter<ProviderRequestsListAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View, val listener: ListListener): RecyclerView.ViewHolder(itemView){
        val name: TextView
        val phone: TextView
        val essential: TextView
        val address: TextView
        val request: Button
        init{
            name = itemView.findViewById(R.id.textViewName)
            phone = itemView.findViewById(R.id.textViewPhoneNo)
            essential = itemView.findViewById(R.id.textViewEssentialName)
            address = itemView.findViewById(R.id.textViewAddress)
            request = itemView.findViewById(R.id.button)
            request.setOnClickListener { listener.onClick(adapterPosition) }
        }
    }
    interface ListListener{
        fun onClick(itemPos: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_requestedpatients, parent, false)
        return ListViewHolder(view, listListener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.request.visibility = View.VISIBLE

        holder.name.text = list[position].name
        holder.phone.text = list[position].phone
        holder.essential.text = map.get(list[position].essentials_id)
        holder.address.text = list[position].address

        if(list[position].address != "Not available"){
            holder.request.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}