package com.example.submissionawal.aboutDetail

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submissionawal.R
import com.example.submissionawal.databinding.ActivityDetailGithubUserBinding
import com.example.submissionawal.ui.ViewDetailUser
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailGithubUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGithubUserBinding
    private lateinit var vmdetail: ViewDetailUser

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailGithubUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val UnameFav : String? =  intent.getStringExtra(EXTRA_USERNAME)
        val idFav : Int? = intent.getIntExtra(EXTRA_ID , 0)
        val avatarFav : String? = intent.getStringExtra(EXTRA_AVATAR)

        val bundles = Bundle()
        bundles.putString(EXTRA_USERNAME, UnameFav)

        vmdetail = ViewModelProvider(this)[ViewDetailUser::class.java]

        if (UnameFav != null) {
            vmdetail.setDetailGithubUser(UnameFav)
            Log.d(TAG, "data tidak terkirim")
        }

        vmdetail.getLoadingState().observe(this){
            binding.pBar.visibility = if(it) View.VISIBLE else View.GONE
        }


        vmdetail.getCariDetailUser().observe(this) {
            if (it != null) {
                binding.apply {
                    if(it.name != null) tvNamagithub.text = it.name
                    else tvNamagithub.text = "Anonim"
                    if(it.location != null) tvLokasi.text = it.location
                    else tvLokasi.text = "Location Unknown"
                    if(it.company != null) tvCompany.text = it.company
                    else tvCompany.text = "not have company"
                    if(it.bio != null) tvBiogithub.text = it.bio
                    else tvBiogithub.text = "Bio is empty"
                    tvUnamegithub.text = "@${it.login}"
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    tvRepos.text = "${it.public_repos} Repository"

                    Glide.with(this@DetailGithubUserActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(imgDetail)

                }


            }
            var FavCheck = true
            CoroutineScope(Dispatchers.IO).launch {
                val count = vmdetail.check(idFav)
                withContext(Dispatchers.Main){
                    if (count != null){
                        if (count > 0) {
                            binding.tgFav.isChecked = true
                            FavCheck = true
                        }
                        else{
                            binding.tgFav.isChecked = false
                            FavCheck = false
                        }
                    }
                }
                binding.tgFav.setOnClickListener{
                    FavCheck =! FavCheck
                    if (FavCheck){
                        vmdetail.add(UnameFav!! , idFav!! , avatarFav!!)
                    }
                    else{
                        vmdetail.remove(idFav!!)
                    }
                    binding.tgFav.isChecked = FavCheck
                }
            }





            val sectionPagerAdapter = com.example.submissionawal.adapter.PagerAdapter(this, bundles
            )
           binding.vpDetail.adapter = sectionPagerAdapter
            val TAB_TITLE = intArrayOf(R.string.tab_followers, R.string.tab_following)

            TabLayoutMediator(binding.tabDetail, binding.vpDetail) {tab , position ->
                tab.text = resources.getString(TAB_TITLE[position])

            }.attach()

        }


    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }

}