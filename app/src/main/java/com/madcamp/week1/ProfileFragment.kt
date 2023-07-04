package com.madcamp.week1

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.madcamp.week1.databinding.FragmentProfileBinding
import com.madcamp.week1.profile.GithubApiObject
import com.madcamp.week1.profile.GithubUserData
import com.madcamp.week1.profile.ServerApiClass
import com.madcamp.week1.profile.SignupData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

  private lateinit var binding: FragmentProfileBinding

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    binding = FragmentProfileBinding.inflate(inflater, container, false)

    ServerApiClass.signup(
        "JeukHwang",
        "strong",
        "jeukhwang@gmail.com",
        object : Callback<SignupData> {
          override fun onResponse(call: Call<SignupData>, response: Response<SignupData>) {
            if (response.isSuccessful) {
              val data = response.body()!!
              activity?.runOnUiThread { binding.nameProfile.text = data.toString() }
              Log.i("API", data.toString())
              Log.i("Retrofit2", "non-error")
            } else {
              Log.e("Retrofit2", "semi-error")
            }
          }

          override fun onFailure(call: Call<SignupData>, t: Throwable) {
            Log.e("Retrofit2", "error")
          }
        })
    binding.searchInputProfile.setOnKeyListener { _, keyCode, event ->
      if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
        val githubId = binding.searchInputProfile.text.toString()
        fetchGithubUser(githubId)
        binding.searchInputProfile.clearFocus()
        return@setOnKeyListener true
      }
      return@setOnKeyListener false
    }
    return binding.root
  }

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
}

/*
private fun fetchServerUser(githubId: String) {
    val call = ServerApiClass.getRetrofitService.getServerUser(("/user/all"))
    call.enqueue(
        object : Callback<SignupData> {
            override fun onResponse(call: Call<SignupData>, response: Response<SignupData>) {
                if (response.isSuccessful) {
                    val serverUser: SignupData = response.body()!!
                    activity?.runOnUiThread {
                        binding.nameProfile.text =
                            if (serverUser.name !== null) "${serverUser.name}" else ""
                        binding.companyProfile.text =
                            if (serverUser.company !== null) "${serverUser.company}" else ""
                        binding.imageProfile.load(serverUser.avatar_url) {
                            crossfade(true)
                            placeholder(android.R.drawable.ic_menu_report_image)
                            transformations(CircleCropTransformation())
                        }
                        binding.blogProfile.text =
                            if (serverUser.blog !== null) "${serverUser.blog}" else ""
                        binding.bioProfile.text = if (serverUser.bio !== null) "${serverUser.bio}" else ""
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