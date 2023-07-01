package com.madcamp.week1.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.madcamp.week1.R

class GridAdapter(var dataSet: ArrayList<String>) : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView
        val textView: TextView

        init {
            imageView = view.findViewById(R.id.gridItemImageGallery)
            textView = view.findViewById(R.id.gridItemTextGallery)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.grid_item_gallery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position]
        viewHolder.textView.setOnClickListener {
            Toast.makeText(
                viewHolder.imageView.context,
                "${dataSet[position]} Click!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}