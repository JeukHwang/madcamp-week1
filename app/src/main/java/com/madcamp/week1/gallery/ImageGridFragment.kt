package com.madcamp.week1.gallery

import android.content.Intent
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
import coil.transform.RoundedCornersTransformation
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

  /*
  fun onItemClick(item: GridItem, imageView: ImageView, textView: TextView) {
    val intent = Intent(requireContext(), GalleryDetailActivity::class.java)

    val imageViewPair = Pair<View, String>(imageView, getString(R.string.image_transition_name))
    val textViewPair = Pair<View, String>(textView, getString(R.string.text_transition_name))
    val options =
        ActivityOptionsCompat.makeSceneTransitionAnimation(
            this.requireActivity(), imageViewPair, textViewPair)
    intent.putExtra(GalleryDetailActivity.extraTitle, item.title)
    intent.putExtra(GalleryDetailActivity.extraPhotoUrl, item.photoUrl)
    startActivity(intent, options.toBundle())
  }*/

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
      viewHolder.imageView.load(gridItem.photoUrl) {
        placeholder(R.drawable.baseline_downloading_24)
        error(R.drawable.outline_error_outline_24)
        transformations(RoundedCornersTransformation(40F))
        crossfade(true)
      }
      viewHolder.itemView.setOnClickListener {
        Toast.makeText(
                this@ImageGridFragment.requireContext(),
                "${gridItem.title} at position $position is clicked!",
                Toast.LENGTH_SHORT)
            .show()
        // onItemClick(gridItem, viewHolder.imageView, viewHolder.textView)

        val intent = Intent(context, GalleryDetailActivity::class.java)
        val bundle = Bundle()

        bundle.putString("info_photourl", gridItem.photoUrl)
        intent.putExtra("bundle_key", bundle)
        startActivity(intent)
      }
    }

    override fun getItemCount(): Int {
      return GridItem.size
    }
  }
}
