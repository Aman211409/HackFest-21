package com.hackfest21.covigenix.UI.PatientServices
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.R

import java.util.*


class PatientServicesHomeAdapter(private val list: ArrayList<HomeItem>, private val context: Context, private val homeListener: HomeListener) : RecyclerView.Adapter<PatientServicesHomeAdapter.HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_servicesection, parent, false)
        return HomeViewHolder(view, homeListener)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.textView.text = list[position].itemName
        holder.imageView.setImageResource(list[position].itemDrawable)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class HomeViewHolder(itemView: View, homeListener: HomeListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var imageView: ImageView
        var textView: TextView
        var homeListener: HomeListener

        init {
            imageView = itemView.findViewById(R.id.image)
            textView = itemView.findViewById(R.id.description)
            this.homeListener = homeListener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            homeListener.onDomainClick(adapterPosition)
        }
    }

    class HomeItem(var itemName: String, var itemDrawable: Int, var domainArg: String)

    interface HomeListener{
        fun onDomainClick(position:Int)
    }

    companion object{
        const val REMDEVISIR = "remdevisir"
        const val PLASMA= "plasma"

    }
}