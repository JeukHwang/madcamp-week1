package com.madcamp.week1.ui.dashboard

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.madcamp.week1.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var getResult: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listData = arrayListOf(
            "Title 1",
            "Title 2",
            "Title 3",
            "Title 4",
            "Title 5",
            "Title 6",
            "Title 7",
            "Title 8"
        )
        val listManager = GridLayoutManager(this.context, 3)
        val listAdapter = GridAdapter(listData)
        val recyclerList = binding.recylcerViewGallery.apply {
            setHasFixedSize(true)
            layoutManager = listManager
            adapter = listAdapter
        }

        val imageView: ImageView = binding.imageView
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val photoUri = it.data?.data!!
                Log.i("PHOTO_URI", photoUri.toString())
                Toast.makeText(activity, photoUri.toString(), Toast.LENGTH_SHORT).show()
                try {
                    val source: ImageDecoder.Source =
                        ImageDecoder.createSource(this.requireContext().contentResolver, photoUri)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    imageView.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        val imageButton: ImageButton = binding.imageButton
        imageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            getResult.launch(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}