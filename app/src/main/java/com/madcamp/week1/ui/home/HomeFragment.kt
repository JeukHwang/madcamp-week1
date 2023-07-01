package com.madcamp.week1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madcamp.week1.MainRvAdapter
import com.madcamp.week1.R
import com.madcamp.week1.databinding.FragmentHomeBinding

import com.madcamp.week1.Info

class HomeFragment : Fragment() {

    var addList = arrayListOf<Info>(
        Info("Song", "https://github.com/minjisong128", "email_minji", "https://avatars.githubusercontent.com/u/110910757?v=4"),
        Info("Hwang", "https://github.com/JeukHwang", "email_jeuk", "https://avatars.githubusercontent.com/u/92910273?v=4"),
        Info("Kim", "https://github.com/kim", "email_kim", "https://avatars.githubusercontent.com/u/6163?v=4"),
        Info("Lee", "https://github.com/lee", "email_lee", "https://avatars.githubusercontent.com/u/2014?v=4"),
        Info("Park", "https://github.com/park", "email_park", "https://avatars.githubusercontent.com/u/135650?v=4"),
        Info("Choi", "https://github.com/choi", "email_choi", "https://avatars.githubusercontent.com/u/10793473?v=4"),
        Info("Lim", "https://github.com/lim", "email_lim", "https://avatars.githubusercontent.com/u/7571747?v=4"),
        Info("Lulu", "https://github.com/lulu", "email_lulu", "https://avatars.githubusercontent.com/u/54672474?v=4"),
        Info("Lala", "https://github.com/lala", "email_lala", "https://avatars.githubusercontent.com/u/106092?v=4"),
        Info("Sookmyung", "https://github.com/Sookmyung", "email_sookmyung", "https://avatars.githubusercontent.com/u/18757873?v=4"),
        Info("Kaist", "https://github.com/kaist", "email_kaist", "https://avatars.githubusercontent.com/u/18757873?v=4"),
        Info("Univ", "https://github.com/Univ", "email_univ", "https://avatars.githubusercontent.com/u/33484326?v=4")
    )

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val mRecyclerView: RecyclerView = binding.mRecyclerView
        val mAdapter = context?.let { MainRvAdapter(it, addList) }

        mRecyclerView.adapter = mAdapter

        val lm = LinearLayoutManager(context)
        mRecyclerView.layoutManager = lm
        mRecyclerView.setHasFixedSize(true)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}