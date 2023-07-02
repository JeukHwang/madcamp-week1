package com.madcamp.week1.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.madcamp.week1.R
import com.madcamp.week1.databinding.FragmentImageGridBinding

class ImageGridFragment : Fragment() {
  private lateinit var binding: FragmentImageGridBinding

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    binding = FragmentImageGridBinding.inflate(inflater, container, false)
    binding.imageGridRecyclerview.apply {
      setHasFixedSize(true)
      layoutManager = GridLayoutManager(this.context, 3)
      adapter = GridAdapter()
    }
    return binding.root
  }

  inner class GridAdapter : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      val imageView: ImageView
      val textView: TextView

      init {
        imageView = view.findViewById(R.id.grid_item_image)
        textView = view.findViewById(R.id.grid_item_text)
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view =
          LayoutInflater.from(parent.context)
              .inflate(R.layout.fragment_image_grid_item, parent, false)
      return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
      val gridItem = GridItem.getData(position)
      viewHolder.imageView.load(gridItem.photoUrl) { crossfade(true) }
      viewHolder.imageView.setOnClickListener {
        Toast.makeText(
                this@ImageGridFragment.requireContext(),
                "${gridItem.title} at position $position is clicked!",
                Toast.LENGTH_SHORT)
            .show()
      }
    }

    override fun getItemCount(): Int {
      return GridItem.size
    }
  }
}
