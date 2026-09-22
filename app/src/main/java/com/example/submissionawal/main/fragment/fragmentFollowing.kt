package com.example.submissionawal.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawal.R
import com.example.submissionawal.aboutDetail.DetailGithubUserActivity
import com.example.submissionawal.adapter.RecyclerViewAdapter
import com.example.submissionawal.databinding.FragFollowersFollowingBinding
import com.example.submissionawal.ui.ViewModelFollowing

class fragmentFollowing : Fragment(R.layout.followers_following) {
    private var _binding: FragFollowersFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterFoll : RecyclerViewAdapter
    private lateinit var vmFoll : ViewModelFollowing
    private lateinit var uname : String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argumen = arguments
        uname = argumen?.getString(DetailGithubUserActivity.EXTRA_USERNAME).toString()

        _binding = FragFollowersFollowingBinding.bind(view)
        adapterFoll = RecyclerViewAdapter()
        adapterFoll.notifyDataSetChanged()

        binding.apply {
            rvHome.setHasFixedSize(true)
            rvHome.layoutManager = LinearLayoutManager(activity)
            rvHome.adapter = adapterFoll
        }

        vmFoll = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ViewModelFollowing::class.java)

        vmFoll.setUserFollowing(uname)
        vmFoll.getLoadingState().observe(viewLifecycleOwner){
            binding.pBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        vmFoll.getCariFollowingUser().observe(viewLifecycleOwner,{
            if (it != null)
                adapterFoll.setListUser(it)

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
