package com.hackfest21.covigenix.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.Model.Request.MyRequestsModel
import com.hackfest21.covigenix.R
import java.text.SimpleDateFormat

class MyRequestsHomeAdapter(val list: List<MyRequestsModel>, val homeListener: HomeListener, val map: HashMap<Int, String>): RecyclerView.Adapter<MyRequestsHomeAdapter.MyRequestViewHolder>() {

    class MyRequestViewHolder(itemView: View, val listener: HomeListener): RecyclerView.ViewHolder(itemView){
        val essential: TextView
        val date: TextView
        val area: TextView
        val viewHospital: Button

        init{
            essential = itemView.findViewById(R.id.textViewPost)
            date = itemView.findViewById(R.id.textViewEssentials)
            area = itemView.findViewById(R.id.textViewPatientName)
            viewHospital = itemView.findViewById(R.id.button)

            viewHospital.setOnClickListener { listener.onClick(adapterPosition) }
        }
    }
    interface HomeListener{
        fun onClick(pos: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carview_patientrequest, parent, false)
        return MyRequestViewHolder(view, homeListener)
    }

    override fun onBindViewHolder(holder: MyRequestViewHolder, position: Int) {
        holder.essential.text = map.get(list[position].essentials_id)
        holder.date.text = (SimpleDateFormat("dd MMMM yyyy HH:mm:ss z")).format(list[position].date)
        holder.area.text = list[position].area
    }

    override fun getItemCount(): Int {
        return list.size
    }
}