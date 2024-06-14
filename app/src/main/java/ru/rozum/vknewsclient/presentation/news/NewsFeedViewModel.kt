package ru.rozum.vknewsclient.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.rozum.vknewsclient.data.repository.NewsFeedRepository
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.StatisticItem

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val initState = NewsFeedScreenState.Initial

    private val _stateScreen = MutableLiveData<NewsFeedScreenState>(initState)
    val stateScreen: LiveData<NewsFeedScreenState> = _stateScreen

    private val repository = NewsFeedRepository(application)

    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            val feedPosts = repository.loadRecommendations()
            _stateScreen.value = NewsFeedScreenState.Posts(posts = feedPosts)
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)
            _stateScreen.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
        }
    }

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