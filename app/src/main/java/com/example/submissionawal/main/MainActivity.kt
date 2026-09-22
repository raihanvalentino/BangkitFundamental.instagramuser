package com.example.submissionawal.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawal.R
import com.example.submissionawal.aboutDetail.DetailGithubUserActivity
import com.example.submissionawal.aboutDetail.DetailGithubUserActivity.Companion.EXTRA_AVATAR
import com.example.submissionawal.aboutDetail.DetailGithubUserActivity.Companion.EXTRA_ID
import com.example.submissionawal.adapter.RecyclerViewAdapter
import com.example.submissionawal.dataClass.GithubUser
import com.example.submissionawal.databinding.ActivityMainBinding
import com.example.submissionawal.main.Fav.FavoriteActivity
import com.example.submissionawal.main.SwitchTheme.SettingPreferenceTheme
import com.example.submissionawal.main.SwitchTheme.ThemeActivity
import com.example.submissionawal.main.SwitchTheme.ViewModelFactory
import com.example.submissionawal.main.SwitchTheme.ThemeViewModel
import com.example.submissionawal.ui.ViewModelMain

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: ViewModelMain
    private lateinit var adapter1: RecyclerViewAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter1 = RecyclerViewAdapter()
        adapter1.setOnItemClickCallback(object : RecyclerViewAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {

                val bundleMain = Bundle()
                bundleMain.putInt(EXTRA_ID, data.id)
                bundleMain.putString(EXTRA_USERNAME, data.login)
                bundleMain.putString(EXTRA_AVATAR , data.avatar_url)

                val detailIntent = Intent(this@MainActivity, DetailGithubUserActivity::class.java)
                detailIntent.putExtras(bundleMain)
                startActivity(detailIntent)


                adapter1.notifyDataSetChanged()

            }
        })

        vm = ViewModelProvider(this).get(ViewModelMain::class.java)

        binding.apply {
            rvHome.layoutManager = LinearLayoutManager(this@MainActivity)
            rvHome.setHasFixedSize(true)
            rvHome.adapter = adapter1


            btnCari.setOnClickListener {
                cariUser()
            }
            etEdit.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    cariUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
            vm.loading.value = true
            cariUserDefault()


        }
        vm.getLoadingState().observe(this){
            binding.pBar.visibility = if (it)View.VISIBLE else View.GONE
        }
        vm.getCariGithubUser().observe(this) {
            if (it != null){
                adapter1.setListUser(it)

            }
        }
        theme()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu , menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menu_fav -> {
                Intent(this,FavoriteActivity::class.java).also {
                    startActivity(it)
                }
                true
            }
            R.id.menu_theme->{
                Intent(this, ThemeActivity::class.java).also{
                    startActivity(it)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }



    }
    private fun theme() {
        val pref = SettingPreferenceTheme.getInstance(dataStore)
        val vmTheme =
            ViewModelProvider(this, ViewModelFactory(pref)).get(ThemeViewModel::class.java)

        vmTheme.getThemeSettings().observe(
            this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    fun cariUser(){
        binding.apply{
            val kataBenda = etEdit.text.toString()
            if (kataBenda != null)
                vm.loading.value = true
            else
                vm.loading.value = false
            vm.setGithubUser(kataBenda)
        }
    }

    fun cariUserDefault(){
        vm.setGithubUser("H")
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}