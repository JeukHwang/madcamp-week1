package com.madcamp.week1.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import com.madcamp.week1.databinding.FragmentProfileBinding
import com.madcamp.week1.ui.profile.api.ApiObject
import com.madcamp.week1.ui.profile.api.GithubUserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

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
                if (response.isSuccessful) {
                    val githubUser: GithubUserData = response.body()!!
                    activity?.runOnUiThread {
                        binding.nameProfile.text =
                            if (githubUser.name !== null) "${githubUser.name}" else ""
                        binding.companyProfile.text =
                            if (githubUser.company !== null) "${githubUser.company}" else ""
                        binding.imageProfile.load(githubUser.avatar_url) {
                            crossfade(true)
                            placeholder(android.R.drawable.ic_menu_report_image)
                            transformations(CircleCropTransformation())
                        }
                        binding.blogProfile.text =
                            if (githubUser.blog !== null) "${githubUser.blog}" else ""
                        binding.bioProfile.text =
                            if (githubUser.bio !== null) "${githubUser.bio}" else ""
                    }
                    Log.i("API", githubUser.toString())
                } else {
                    Toast.makeText(
                        this@ProfileFragment.requireContext(),
                        "Cannot get Github Profile...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<GithubUserData>, t: Throwable) {
                Toast.makeText(
                    this@ProfileFragment.requireContext(),
                    "Cannot get Github Profile :(",
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
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        profileViewModel.text.observe(viewLifecycleOwner) {
        }
        val button = binding.searchButtonProfile
        button.setOnClickListener {
            val githubId = binding.searchInputProfile.text.toString()
            fetchGithubUser(githubId)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}