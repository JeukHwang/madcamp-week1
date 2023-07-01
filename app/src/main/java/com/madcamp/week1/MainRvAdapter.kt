package com.madcamp.week1

import android.content.Context
import androidx.recyclerview.widget.RecyclerView // import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.madcamp.week1.ui.home.HomeFragment

class MainRvAdapter(val context: Context, val addList: ArrayList<Info>) :
    RecyclerView.Adapter<MainRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_rv_item, parent, false)
        return Holder(view)
    }

    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        TODO("Not yet implemented")
    }*/

    override fun onBindViewHolder(holder: Holder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return addList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int, payloads: MutableList<Any>) {
        holder?.bind(addList[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val userPhoto = itemView?.findViewById<ImageView>(R.id.userPhotoImg)
        val userId = itemView?.findViewById<TextView>(R.id.userLoginTv)
        val userUrl = itemView?.findViewById<TextView>(R.id.userUrlTv)
        val userEmail = itemView?.findViewById<TextView>(R.id.userEmailTv)

        fun bind (info: Info, context: Context) {
            if (info.photo != "") {
                userPhoto?.load(info.photo) // url로 가져오기
            } else {
                userPhoto?.setImageResource(R.mipmap.ic_launcher)
            }
            userId?.text = info.id
            userUrl?.text = info.github_url
            userEmail?.text = info.email
        }
    }
}