package com.example.submissionawal.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionawal.dataClass.GithubUser
import com.example.submissionawal.databinding.ItemHomeBinding

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback

    }
    private val item = ArrayList<GithubUser>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setListUser(userGit : ArrayList<GithubUser>){
        item.clear()
        item.addAll(userGit)
        notifyDataSetChanged()
    }

    inner class RecyclerViewHolder (private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root){
        fun binding(git: GithubUser){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(git)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(git.avatar_url)
                    .centerCrop()
                    .into(imgHome)
                tvUsername.text = "@${git.login}"
                tvNama.text = git.id.toString()
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return RecyclerViewHolder((view))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.binding(item[position])
    }

    override fun getItemCount(): Int = item.size

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }
}