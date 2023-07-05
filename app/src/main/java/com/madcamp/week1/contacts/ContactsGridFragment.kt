package com.madcamp.week1.contacts

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
import coil.transform.CircleCropTransformation
import com.madcamp.week1.R
import com.madcamp.week1.databinding.FragmentContactsGridBinding
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

  inner class GridAdapter : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      private val userPhoto: ImageView
      private val userName: TextView
      private val userClass: TextView
      private val userExplanation: TextView
      private val context = binding.root.context // 추가

      init {
        userPhoto = view.findViewById(R.id.contacts_image)
        userName = view.findViewById(R.id.contacts_name)
        userClass = view.findViewById(R.id.contacts_class_text)
        userExplanation = view.findViewById(R.id.contacts_explanation_text)
      }

      fun bind(userProfile: UserProfile) {
        if (userProfile.profilePhoto != "") {
          userPhoto.load(userProfile.profilePhoto) {
            placeholder(R.drawable.baseline_person_24)
            transformations(CircleCropTransformation())
            crossfade(500)
          }
        } else {
          userPhoto.setImageResource(R.mipmap.ic_launcher)
        }
        userName.text = userProfile.name
        userClass.text = "${userProfile.classNum}분반"
        userExplanation.text = userProfile.explanation

        itemView.setOnClickListener {
          val intent = Intent(this.context, ContactsDetailActivity::class.java)
          intent.putExtra("profile", userProfile)
          /*          val imageViewPair =
              Pair<View, String>(imageView, getString(R.string.image_transition_name))
          val textViewPair = Pair<View, String>(textView, getString(R.string.text_transition_name))
          val options =
              ActivityOptionsCompat.makeSceneTransitionAnimation(
                  this.requireActivity(), imageViewPair, textViewPair)
          startActivity(intent, options.toBundle())*/
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
