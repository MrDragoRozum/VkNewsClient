package ru.rozum.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.PostComment
import ru.rozum.vknewsclient.domain.StatisticItem
import ru.rozum.vknewsclient.ui.theme.HomeScreenState

class MainViewModel : ViewModel() {

    private val list = mutableListOf<FeedPost>().apply {
        repeat(5) {
            add(FeedPost(it))
        }
    }

    private val comments = mutableListOf<PostComment>().apply {
        repeat(20) {
            add(
                PostComment(id = it)
            )
        }
    }

    private val initState = HomeScreenState.Posts(posts = list)

    private val _stateScreen = MutableLiveData<HomeScreenState>(initState)
    val stateScreen: LiveData<HomeScreenState> = _stateScreen

    private var savedState: HomeScreenState? = initState

    fun showComment(feedPost: FeedPost) {
        savedState = _stateScreen.value
        _stateScreen.value = HomeScreenState.Comments(feedPost, comments)
    }

    fun closeComments() {
        _stateScreen.value = savedState ?: HomeScreenState.Posts(posts = list)
    }

    fun updateCount(postId: Int, item: StatisticItem) {
        val currentState = _stateScreen.value
        if(currentState !is HomeScreenState.Posts) return

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
        _stateScreen.value = HomeScreenState.Posts(posts = modifiedList)
    }


    fun removePost(post: FeedPost) {
        val currentState = _stateScreen.value
        if(currentState !is HomeScreenState.Posts) return

        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(post)
        _stateScreen.value = HomeScreenState.Posts(posts = modifiedList)
    }
}