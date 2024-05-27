package ru.rozum.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.StatisticItem

class MainViewModel : ViewModel() {

    private val list = mutableListOf<FeedPost>().apply {
        repeat(5) {
            add(FeedPost(it))
        }
    }.toList()

    private val _feedPosts = MutableLiveData(list)
    val feedPosts: LiveData<List<FeedPost>> = _feedPosts

    fun updateCount(postId: Int, item: StatisticItem) {
        val modifiedList = _feedPosts.getValueNotNull()
        val oldPost = modifiedList[postId]

        val newStatistics = oldPost.statistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        modifiedList[postId] = oldPost.copy(statistics = newStatistics)
        _feedPosts.value = modifiedList
    }


    fun removePost(post: FeedPost) {
        val modifiedList = _feedPosts.getValueNotNull()
        modifiedList.remove(post)
        _feedPosts.value = modifiedList
    }

    private fun LiveData<List<FeedPost>>.getValueNotNull(): MutableList<FeedPost> =
        this.value?.toMutableList() ?: mutableListOf()
}