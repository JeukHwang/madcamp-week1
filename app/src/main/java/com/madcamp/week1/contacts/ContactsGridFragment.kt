package com.madcamp.week1.contacts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.madcamp.week1.R
import com.madcamp.week1.databinding.FragmentContactsGridBinding

class ContactsGridFragment : Fragment() {

  private lateinit var binding: FragmentContactsGridBinding

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
    return binding.root
  }

  inner class GridAdapter : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      private val userPhoto: ImageView
      private val userId: TextView
      private val userUrl: TextView
      private val userEmail: TextView

      private val context = binding.root.context

      init {
        userPhoto = view.findViewById(R.id.userPhotoImg)
        userId = view.findViewById(R.id.userLoginTv)
        userUrl = view.findViewById(R.id.userUrlTv)
        userEmail = view.findViewById(R.id.userEmailTv)
      }

      fun bind(info: ContactsInfo) {
        if (info.photo != "") {
          userPhoto.load(info.photo) // url로 가져오기
        } else {
          userPhoto.setImageResource(R.mipmap.ic_launcher)
        }
        userId.text = info.id
        userUrl.text = info.github_url
        userEmail.text = info.email

        itemView.setOnClickListener {
          var intent = Intent(this.context, ContactsDetailActivity::class.java)
          var bundle = Bundle()

          bundle.putString("info_photo", info.photo)
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
      return ContactsInfo.dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(ContactsInfo.dataset[position])
    }
  }
}
