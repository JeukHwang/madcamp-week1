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
        Info("Song", "https://github.com/minjisong128", "email_minji", "p1"), // https://avatars.githubusercontent.com/u/110910757?v=4
        Info("Hwang", "https://github.com/JeukHwang", "email_jeuk", "p2"), // https://avatars.githubusercontent.com/u/92910273?v=4
        Info("Kim", "https://github.com/kim", "email_kim", "p3"), // https://avatars.githubusercontent.com/u/6163?v=4
        Info("Lee", "https://github.com/lee", "email_lee", "p4"), // https://avatars.githubusercontent.com/u/2014?v=4
        Info("Park", "https://github.com/park", "email_park", "p5"), // https://avatars.githubusercontent.com/u/135650?v=4
        Info("Choi", "https://github.com/choi", "email_choi", "p6"), // https://avatars.githubusercontent.com/u/10793473?v=4
        Info("Lim", "https://github.com/lim", "email_lim", "p7"), // https://avatars.githubusercontent.com/u/7571747?v=4
        Info("Lim", "https://github.com/lim", "email_lim", "p7"), // https://avatars.githubusercontent.com/u/7571747?v=4
        Info("Song", "https://github.com/minjisong128", "email_minji", "p1"), // https://avatars.githubusercontent.com/u/110910757?v=4
        Info("Hwang", "https://github.com/JeukHwang", "email_jeuk", "p2"), // https://avatars.githubusercontent.com/u/92910273?v=4
        Info("Kim", "https://github.com/kim", "email_kim", "p3"), // https://avatars.githubusercontent.com/u/6163?v=4
        Info("Lee", "https://github.com/lee", "email_lee", "p4"), // https://avatars.githubusercontent.com/u/2014?v=4
    )

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView // 기존의 HomeActivity.kt에서 추가

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        val mRecyclerView = findViewById<RecyclerView>(R.id.mRecyclerView)
        val mAdapter = MainRvAdapter(this, addList)

        mRecyclerView.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        mRecyclerView.layoutManager = lm
        mRecyclerView.setHasFixedSize(true)
    }*/
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