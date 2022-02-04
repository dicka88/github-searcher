package com.dickaismaji.github.ui.favorite

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dickaismaji.github.database.FavoriteUser
import com.dickaismaji.github.databinding.ItemUserGithubCardBinding
import com.dickaismaji.github.helper.FavoriteUserDiffCallback

class ListFavoriteUser(var binding: ItemUserGithubCardBinding): RecyclerView.ViewHolder(binding.root)

class FavoriteUserListAdapter: RecyclerView.Adapter<ListFavoriteUser>() {
  interface OnItemClickCallback {
    fun onItemClicked(data: FavoriteUser)
  }

  private val listFavoriteUser = ArrayList<FavoriteUser>()

  private lateinit var onItemClickCallback: OnItemClickCallback

  fun setListFavoriteUser(listFavoriteUser: List<FavoriteUser>) {
    val diffCallback = FavoriteUserDiffCallback(this.listFavoriteUser, listFavoriteUser)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    this.listFavoriteUser.clear()
    this.listFavoriteUser.addAll(listFavoriteUser)
    diffResult.dispatchUpdatesTo(this)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFavoriteUser {
    val binding = ItemUserGithubCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ListFavoriteUser(binding)
  }

  override fun onBindViewHolder(holder: ListFavoriteUser, position: Int) {
    val login = listFavoriteUser[position].login
    val avatarUrl = listFavoriteUser[position].avatar_url

    Glide.with(holder.itemView.context)
      .load("${avatarUrl}&s=48")
      .placeholder(ColorDrawable(Color.GRAY))
      .error(ColorDrawable(Color.RED))
      .circleCrop()
      .into(holder.binding.imgAvatar)

    holder.binding.apply {
      tvUsername.text = login
    }

    holder.itemView.setOnClickListener {
      onItemClickCallback.onItemClicked(listFavoriteUser[position])
    }
  }

  override fun getItemCount(): Int = listFavoriteUser.count()

  fun setItemOnClickCallback(onItemClickCallback: OnItemClickCallback) {
    this.onItemClickCallback = onItemClickCallback
  }
}