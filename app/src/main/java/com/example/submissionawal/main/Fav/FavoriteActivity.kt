package com.example.submissionawal.main.Fav

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawal.aboutDetail.DetailGithubUserActivity
import com.example.submissionawal.adapter.RecyclerViewAdapter
import com.example.submissionawal.dataClass.GithubUser
import com.example.submissionawal.databinding.ActivityFavoriteBinding
import com.example.submissionawal.dbfav.FavUsers
import com.example.submissionawal.main.MainActivity
import com.example.submissionawal.ui.ViewModelFavUser

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter1: RecyclerViewAdapter
    private lateinit var vmFavUser: ViewModelFavUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter1 = RecyclerViewAdapter()
        adapter1.notifyDataSetChanged()
        vmFavUser = ViewModelProvider(this).get(ViewModelFavUser::class.java)

        adapter1.setOnItemClickCallback(object : RecyclerViewAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {

                val bundleMain = Bundle()
                bundleMain.putInt(DetailGithubUserActivity.EXTRA_ID, data.id)
                bundleMain.putString(MainActivity.EXTRA_USERNAME, data.login)
                bundleMain.putString(DetailGithubUserActivity.EXTRA_AVATAR, data.avatar_url)

                val FavIntentUser =
                    Intent(this@FavoriteActivity, DetailGithubUserActivity::class.java)
                FavIntentUser.putExtras(bundleMain)
                startActivity(FavIntentUser)
            }
        })

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter1
        }
        vmFavUser.getFavoriteUsers()?.observe(this, {
            if (it != null) {
                val listFav = mapList (it)
                adapter1.setListUser(listFav)
            }
        })

    }

    private fun mapList(users: List<FavUsers>): ArrayList<GithubUser> {
        val listGithubUser = ArrayList<GithubUser>()
        for (user in users){
            val userFavMapped = GithubUser(
                user.login,
                user.avatar_url,
                user.id
            )
            listGithubUser.add(userFavMapped)

        }
return listGithubUser
    }
}