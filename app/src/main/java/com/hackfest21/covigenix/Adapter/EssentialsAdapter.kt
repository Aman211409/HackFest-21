package com.hackfest21.covigenix.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.UI.PatientServices.PatientServicesHomeAdapter

class EssentialsAdapter(private val list: List<Essential>, private val essentialListener: EssentialListener) : RecyclerView.Adapter<EssentialsAdapter.EssentialViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EssentialViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_essential, parent, false)
        return EssentialViewHolder(view, essentialListener)
    }

    override fun onBindViewHolder(holder: EssentialViewHolder, position: Int) {
        holder.essentialName.text = list[position].name
    }

    override fun getItemCount(): Int = list.size

    class Essential(val name: String, val id: Int, var checked: Boolean)

    class EssentialViewHolder(itemView: View, essentialListener: EssentialListener): RecyclerView.ViewHolder(itemView) {
        var essentialName: TextView
        var checkBox: CheckBox
        var listener: EssentialListener

        init{
            essentialName = itemView.findViewById(R.id.essentialName)
            checkBox = itemView.findViewById(R.id.checkBox)
            listener = essentialListener
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->  listener.toggle(adapterPosition)}
        }
    }

    interface EssentialListener{
        fun toggle(itemPos: Int)
    }
}