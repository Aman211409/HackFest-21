package com.hackfest21.covigenix.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.UI.PatientServices.PatientServicesHomeAdapter

class ProviderRequestsHomeAdapter(var list: ArrayList<Item>, var homeListener: HomeListener): RecyclerView.Adapter<ProviderRequestsHomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(itemView: View, listener: HomeListener): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var imageView: ImageView
        var textView: TextView
        var homeListener: HomeListener

        init {
            imageView = itemView.findViewById(R.id.image)
            textView = itemView.findViewById(R.id.description)
            this.homeListener = listener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            homeListener.onClick(adapterPosition)
        }
    }
    class Item(val name: String, val itemDrawable: Int, val id: Int)

    interface HomeListener{
        fun onClick(itemPos: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_servicesection, parent, false)
        return HomeViewHolder(view, homeListener)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.textView.text = list[position].name
        holder.imageView.setImageResource(list[position].itemDrawable)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}