package com.madcamp.week1.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.madcamp.week1.databinding.FragmentNotificationsBinding
import com.madcamp.week1.ui.notifications.api.ApiObject
import com.madcamp.week1.ui.notifications.api.GithubUserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private fun fetchGithubUser(githubId: String) {
        val call = ApiObject.getRetrofitService.getGithubUser("users/$githubId")
        call.enqueue(object : Callback<GithubUserData> {
            override fun onResponse(
                call: Call<GithubUserData>,
                response: Response<GithubUserData>
            ) {
                Toast.makeText(
                    this@NotificationsFragment.requireContext(),
                    "Call Success",
                    Toast.LENGTH_SHORT
                ).show()
                if (response.isSuccessful) {
                    val githubUser: GithubUserData = response.body()!!
                    val textView: TextView = binding.notiTextview
                    activity?.runOnUiThread {
                        textView.text = "${githubUser.name}\n${githubUser.avatar_url}"
                    }
                    Log.i("API", githubUser.toString())
                } else {
                    Toast.makeText(
                        this@NotificationsFragment.requireContext(),
                        "Call Failed2",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<GithubUserData>, t: Throwable) {
                Toast.makeText(
                    this@NotificationsFragment.requireContext(),
                    "Call Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        notificationsViewModel.text.observe(viewLifecycleOwner) {
        }
        val button = binding.notiButton
        button.setOnClickListener {
            val githubId = binding.notiEdittext.text.toString()
            fetchGithubUser(githubId)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}