package com.madcamp.week1.contacts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.madcamp.week1.R
import com.madcamp.week1.databinding.FragmentContactsGridBinding
import com.madcamp.week1.gallery.GalleryDetailActivity
import com.madcamp.week1.gallery.GridItem
import com.madcamp.week1.profile.ServerApiClass
import com.madcamp.week1.profile.UserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactsGridFragment : Fragment() {

  private lateinit var binding: FragmentContactsGridBinding
  private var dataset: ArrayList<UserProfile> = ArrayList()

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    binding = FragmentContactsGridBinding.inflate(inflater, container, false)
    binding.contactsGridRecyclerview.apply {
      setHasFixedSize(true)
      layoutManager = GridLayoutManager(this.context, 1)
      adapter = GridAdapter()
    }
    (binding.contactsGridRecyclerview.adapter as GridAdapter).updateData()
    return binding.root
  }

  fun onItemClick(item: GridItem, imageView: ImageView, textView: TextView) {
    val intent = Intent(requireContext(), ContactsDetailActivity::class.java)

    val imageViewPair = Pair<View, String>(imageView, getString(R.string.image_transition_name))
    val textViewPair = Pair<View, String>(textView, getString(R.string.text_transition_name))
    val options =
        ActivityOptionsCompat.makeSceneTransitionAnimation(
            this.requireActivity(), imageViewPair, textViewPair)
    intent.putExtra(GalleryDetailActivity.extraTitle, item.title)
    intent.putExtra(GalleryDetailActivity.extraPhotoUrl, item.photoUrl)
    startActivity(intent, options.toBundle())
  }

  inner class GridAdapter : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      private val userPhoto: ImageView
      private val userId: TextView
      private val userUrl: TextView
      private val userEmail: TextView
      private val context = binding.root.context // 추가

      init {
        userPhoto = view.findViewById(R.id.userPhotoImg)
        userId = view.findViewById(R.id.userLoginTv)
        userUrl = view.findViewById(R.id.userUrlTv)
        userEmail = view.findViewById(R.id.userEmailTv)
      }

      fun bind(info: UserProfile) {
        if (info.profilePhoto != "") {
          userPhoto.load(info.profilePhoto) // url로 가져오기
        } else {
          userPhoto.setImageResource(R.mipmap.ic_launcher)
        }
        userId.text = info.id
        userUrl.text = info.githubId
        userEmail.text = info.email

        // 추가
        itemView.setOnClickListener {
          val intent = Intent(this.context, ContactsDetailActivity::class.java)
          val bundle = Bundle()
          //           bundle = intent.getBundleExtra("bundle")
          bundle.putString("info_id", info.id)
          bundle.putString("info_email", info.email)
          intent.putExtra("bundle_key", bundle)
          startActivity(intent)
        }
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter.ViewHolder {
      val view =
          LayoutInflater.from(parent.context)
              .inflate(R.layout.fragment_contacts_grid_item, parent, false)
      return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(dataset[position])
    }

    fun updateData() {
      dataset.clear()

      ServerApiClass.getAll(
          object : Callback<Array<UserProfile>> {
            override fun onResponse(
                call: Call<Array<UserProfile>>,
                response: Response<Array<UserProfile>>
            ) {
              if (response.isSuccessful) {
                val userProfiles = response.body()!!
                dataset.addAll(userProfiles)
                dataset.addAll(userProfiles)
                dataset.addAll(userProfiles)
                dataset.addAll(userProfiles)
                dataset.addAll(userProfiles)

                notifyDataSetChanged()
              } else {
                Toast.makeText(activity, R.string.onSemiFailure, Toast.LENGTH_SHORT).show()
              }
            }

            override fun onFailure(call: Call<Array<UserProfile>>, t: Throwable) {
              Toast.makeText(activity, R.string.onFailure, Toast.LENGTH_SHORT).show()
            }
          })
    }
  }
}
