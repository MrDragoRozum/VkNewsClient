package ru.rozum.vknewsclient.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.launch
import ru.rozum.vknewsclient.data.model.toPosts
import ru.rozum.vknewsclient.data.network.ApiFactory
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.StatisticItem

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val initState = NewsFeedScreenState.Initial

    private val _stateScreen = MutableLiveData<NewsFeedScreenState>(initState)
    val stateScreen: LiveData<NewsFeedScreenState> = _stateScreen

    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            val storage = VKPreferencesKeyValueStorage(getApplication())
            val token = VKAccessToken.restore(storage) ?: return@launch
            ApiFactory.apiService.loadRecommendations(token.accessToken).also { response ->
                val feedPosts = response.toPosts()
                _stateScreen.value = NewsFeedScreenState.Posts(posts = feedPosts)
            }
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