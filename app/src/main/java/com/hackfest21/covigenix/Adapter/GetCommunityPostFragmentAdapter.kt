package com.hackfest21.covigenix.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.Model.CommunityPostModel
import com.hackfest21.covigenix.R
import kotlinx.android.synthetic.main.cardview_createcommunitypost.view.*
import kotlinx.android.synthetic.main.cardview_createcommunitypost.view.textViewName
import kotlinx.android.synthetic.main.cardview_createcommunitypost.view.textViewPhoneNo
import kotlinx.android.synthetic.main.cardview_viewcommnunitypost.view.*

open class GetCommunityPostFragmentAdapter(val postList: List<CommunityPostModel>): RecyclerView.Adapter<GetCommunityPostFragmentAdapter.GetCommunityPostViewHolder>() {

    class GetCommunityPostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val textViewName = itemView.textViewName
        val textViewPhoneNo = itemView.textViewPhoneNo
        val textViewArea = itemView.textViewArea
        val textViewItemName = itemView.textViewItemName
        val textViewDetails = itemView.textViewDescription
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetCommunityPostViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_viewcommnunitypost, parent, false)
        return GetCommunityPostViewHolder(v)
    }

    override fun onBindViewHolder(holder: GetCommunityPostViewHolder, position: Int) {
        holder.textViewName.text = postList?.get(position)?.name
        holder.textViewPhoneNo.text = postList?.get(position)?.phone
        holder.textViewArea.text = postList?.get(position)?.area
        holder.textViewItemName.text = postList?.get(position)?.itemName
        holder.textViewDetails.text = postList?.get(position)?.details

    }

    override fun getItemCount(): Int = postList!!.size

}