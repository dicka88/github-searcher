package com.dickaismaji.github.ui.user

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dickaismaji.github.databinding.ItemUserGithubCardBinding
import com.dickaismaji.github.models.response.search.user.UserItem

class ListUserGithubViewHolder(var binding: ItemUserGithubCardBinding): RecyclerView.ViewHolder(binding.root)

class ListUsersGithubAdapter(
  private val listUsers: List<UserItem>
): RecyclerView.Adapter<ListUserGithubViewHolder>() {

  interface OnItemClickCallback {
    fun onItemClicked(data: UserItem)
  }

  private lateinit var onItemClickCallback: OnItemClickCallback

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserGithubViewHolder {
    val binding = ItemUserGithubCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ListUserGithubViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ListUserGithubViewHolder, position: Int) {
    val login = listUsers[position].login
    val avatarUrl = listUsers[position].avatarUrl

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
      onItemClickCallback.onItemClicked(listUsers[position])
    }
  }

  override fun getItemCount(): Int = listUsers.count()

  fun setItemOnClickCallback(onItemClickCallback: OnItemClickCallback) {
    this.onItemClickCallback = onItemClickCallback
  }
}