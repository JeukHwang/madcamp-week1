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
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.madcamp.week1.R
import com.madcamp.week1.databinding.FragmentDashboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val call = ApiObject.getRetrofitService.getCoinAll()
        call.enqueue(object : Callback<List<Coin>> {
            override fun onResponse(call: Call<List<Coin>>, response: Response<List<Coin>>) {
                Toast.makeText(
                    this@DashboardFragment.requireContext(),
                    "Call Success",
                    Toast.LENGTH_SHORT
                ).show()
                if (response.isSuccessful) {
                    val coinList = response.body() ?: listOf()
                    Log.i("API", coinList.toString())
                }
            }

            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {
                Toast.makeText(
                    this@DashboardFragment.requireContext(),
                    "Call Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
//        var resultText = null
//        try {
//            resultText = Task().execute().get()
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        } catch (e: ExecutionException) {
//            e.printStackTrace()
//        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val imageView: ImageView = binding.imageView
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                activity?.runOnUiThread {
                    textView.text = getString(R.string.dashboard_success)
                }
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
            } else {
                textView.text = getString(R.string.dashboard_failure)
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