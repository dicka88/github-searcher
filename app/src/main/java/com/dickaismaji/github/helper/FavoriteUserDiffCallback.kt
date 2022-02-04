package com.dickaismaji.github.helper

import androidx.recyclerview.widget.DiffUtil
import com.dickaismaji.github.database.FavoriteUser

class FavoriteUserDiffCallback(
  private val mOldFavoriteUserList: List<FavoriteUser>,
  private val mNewFavoriteUserList: List<FavoriteUser>
): DiffUtil.Callback() {
  override fun getOldListSize(): Int {
    return mOldFavoriteUserList.size
  }
  override fun getNewListSize(): Int {
    return mNewFavoriteUserList.size
  }
  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return mOldFavoriteUserList[oldItemPosition].id == mNewFavoriteUserList[newItemPosition].id
  }
  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val oldEmployee = mOldFavoriteUserList[oldItemPosition]
    val newEmployee = mNewFavoriteUserList[newItemPosition]
    return oldEmployee.login == newEmployee.login
            && oldEmployee.avatar_url == newEmployee.avatar_url
  }
}