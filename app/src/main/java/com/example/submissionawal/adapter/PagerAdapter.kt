package com.example.submissionawal.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submissionawal.R
import com.example.submissionawal.main.fragment.fragmentFollowers
import com.example.submissionawal.main.fragment.fragmentFollowing

class PagerAdapter (activity: FragmentActivity, data:Bundle) : FragmentStateAdapter(activity){

    private var fragmentBundle :  Bundle

    init {
        fragmentBundle = data
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position : Int): Fragment{
        var fragment : Fragment? = null

        val _FollowingFragment = fragmentFollowing()
        val _FollowersFragment = fragmentFollowers()

        _FollowingFragment.arguments = this.fragmentBundle
        _FollowersFragment.arguments= this.fragmentBundle


        when (position){
            0 -> fragment =_FollowersFragment
            1 -> fragment = _FollowingFragment
        }
        return fragment as Fragment
    }
    }

