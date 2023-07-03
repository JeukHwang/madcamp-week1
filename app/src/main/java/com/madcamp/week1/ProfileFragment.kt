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
import com.madcamp.week1.profile.ApiObject
import com.madcamp.week1.profile.GithubUserData
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
    val call = ApiObject.getRetrofitService.getGithubUser("users/$githubId")
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
