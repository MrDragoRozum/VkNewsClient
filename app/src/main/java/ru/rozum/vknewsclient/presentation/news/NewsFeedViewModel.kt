package ru.rozum.vknewsclient.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.StatisticItem

class NewsFeedViewModel : ViewModel() {

    private val list = mutableListOf<FeedPost>().apply {
        repeat(5) {
            add(FeedPost(it))
        }
    }

    private val initState = NewsFeedScreenState.Posts(posts = list)

    private val _stateScreen = MutableLiveData<NewsFeedScreenState>(initState)
    val stateScreen: LiveData<NewsFeedScreenState> = _stateScreen


    fun updateCount(postId: Int, item: StatisticItem) {
        val currentState = _stateScreen.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val modifiedList = currentState.posts.toMutableList()
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
        _stateScreen.value = NewsFeedScreenState.Posts(posts = modifiedList)
    }

    fun removePost(post: FeedPost) {
        val currentState = _stateScreen.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(post)
        _stateScreen.value = NewsFeedScreenState.Posts(posts = modifiedList)
    }
}