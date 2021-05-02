package com.hackfest21.covigenix.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.hackfest21.covigenix.Model.Request.ProviderResponseModel
import com.hackfest21.covigenix.R
import kotlinx.android.synthetic.main.cardview_hospitalsnearby.view.*
import org.tensorflow.lite.TensorFlowLite.init

class PatientRequestApplyAdapter(val list: ArrayList<ProviderResponseModel>, val applyListener: ApplyListener): RecyclerView.Adapter<PatientRequestApplyAdapter.ProviderViewHolder>() {

    interface ApplyListener{
        fun onCheck(itemPos: Int)
        fun onLongPress(itemPos: Int)
    }

    class ProviderViewHolder(itemView: View, var listener: ApplyListener): RecyclerView.ViewHolder(itemView) {

        val name: TextView
        val area: TextView
        val phone: TextView
        val checkBox: MaterialCheckBox
        val  nav_to_map:Button

        init{
            name = itemView.findViewById(R.id.textViewHospitalName)
            area = itemView.findViewById(R.id.textViewArea)
            phone = itemView.findViewById(R.id.textViewPhoneNo)
            checkBox = itemView.findViewById(R.id.checkBox)
            nav_to_map=itemView.findViewById((R.id.nav_to_map))

            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                listener.onCheck(adapterPosition)
            }
            nav_to_map.setOnClickListener{
                listener.onLongPress(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_hospitalsnearby, parent, false)
        return ProviderViewHolder(view, applyListener)
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {

        holder.name.text = list[position].name
        holder.phone.text = list[position].phone
        holder.area.text = list[position].area

        holder.checkBox.setOnCheckedChangeListener(null)
        if(list[position].checkStatus == -1){
            holder.checkBox.visibility = INVISIBLE
        }else if(list[position].checkStatus == 0){
            holder.checkBox.visibility = VISIBLE
            holder.checkBox.isChecked = false
            holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                applyListener.onCheck(position)
            }
        }else{
            holder.checkBox.visibility = VISIBLE
            holder.checkBox.isChecked = true
            holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                applyListener.onCheck(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}