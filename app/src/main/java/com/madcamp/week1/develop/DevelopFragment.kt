package com.madcamp.week1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.madcamp.week1.databinding.FragmentDevelopBinding
import com.madcamp.week1.profile.ServerApiClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DevelopFragment : Fragment() {

    private lateinit var binding: FragmentDevelopBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDevelopBinding.inflate(inflater, container, false)

        ServerApiClass.getServerUser(
            "JeukHwang",
            "strong",
            "jeukhwang@gmail.com",
            "https://images.unsplash.com/photo-1426604966848-d7adac402bff?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
            "jeukhwang",
            "",
            "",
            "222222",
            object : Callback<Array<UserProfileData>> {
                override fun onResponse(
                    call: Call<Array<UserProfileData>>,
                    response: Response<Array<UserProfileData>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()!!
                        activity?.runOnUiThread { binding.developName.text = data.toString() }
                        Log.i("API", data.toString())
                        Log.i("Retrofit2", "non-error")
                    } else {
                        Log.e("Retrofit2", "semi-error")
                    }
                }

                override fun onFailure(call: Call<Array<UserProfileData>>, t: Throwable) {
                    Log.e("Retrofit2", "error")
                }
            })
        fetchServerUser()
        /*binding.searchInputProfile.setOnKeyListener { _, keyCode, event ->
          if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
            //val userEmail = binding.searchInputProfile.text.toString()
            fetchServerUser()
            binding.searchInputProfile.clearFocus()
            return@setOnKeyListener true
          }
          return@setOnKeyListener false
        }*/
        return binding.root
    }

    private fun fetchServerUser() {
        val call = ServerApiClass.getRetrofitService.getServerUser()
        call.enqueue(
            object : Callback<Array<UserProfileData>> {
                override fun onResponse(
                    call: Call<Array<UserProfileData>>,
                    response: Response<Array<UserProfileData>>
                ) {
                    if (response.isSuccessful) {
                        val serverUser: Array<UserProfileData> = response.body()!!
                        activity?.runOnUiThread {
                            for (i in 0..serverUser.size - 1 step (1)) {
                                binding.developId.text =
                                    if (serverUser[i].id !== null) "${serverUser[i].id}" else ""
                                binding.developEmail.text =
                                    if (serverUser[i].email !== null) "${serverUser[i].email}" else ""
                                binding.developName.text =
                                    if (serverUser[i].name !== null) "${serverUser[i].name}" else ""
                                binding.developGithubId.text =
                                    if (serverUser[i].githubId !== null) "${serverUser[i].githubId}" else ""
                                binding.developInstagramId.text =
                                    if (serverUser[i].instagramId !== null) "${serverUser[i].instagramId}" else ""
                                binding.developLinkedInId.text =
                                    if (serverUser[i].linkedInId !== null) "${serverUser[i].linkedInId}" else ""
                                binding.developExplanation.text =
                                    if (serverUser[i].githubId !== null) "${serverUser[i].githubId}" else ""

                                binding.developImage.load(serverUser[i].profilePhoto) {
                                    crossfade(true)
                                    placeholder(android.R.drawable.ic_menu_report_image)
                                    transformations(CircleCropTransformation())
                                }
                            }
                        }
                        Log.i("API", serverUser.toString())
                    } else {
                        Toast.makeText(
                            this@DevelopFragment.requireContext(),
                            "Cannot get Profile from server...",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onFailure(call: Call<Array<UserProfileData>>, t: Throwable) {
                    Toast.makeText(
                        this@DevelopFragment.requireContext(),
                        "Cannot get Profile from server :(",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
    }

    /*
    private fun fetchGithubUser(githubId: String) {
        val call = GithubApiObject.getRetrofitService.getGithubUser("users/$githubId")
        call.enqueue(
            object : Callback<GithubUserData> {
                override fun onResponse(call: Call<GithubUserData>, response: Response<GithubUserData>) {
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
                            binding.bioProfile.text = if (githubUser.bio !== null) "${githubUser.bio}" else ""
                        }
                        Log.i("API", githubUser.toString())
                    } else {
                        Toast.makeText(
                            this@ProfileFragment.requireContext(),
                            "Cannot get Github Profile...",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<GithubUserData>, t: Throwable) {
                    Toast.makeText(
                        this@ProfileFragment.requireContext(),
                        "Cannot get Github Profile :(",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
     */
}
