package ru.rozum.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.StatisticItem
import ru.rozum.vknewsclient.ui.theme.HomeScreenState

class MainViewModel : ViewModel() {

    private val list = mutableListOf<FeedPost>().apply {
        repeat(5) {
            add(FeedPost(it))
        }
    }.toList()

    private val initState = HomeScreenState.Posts(posts = list)

    private val _stateScreen = MutableLiveData<HomeScreenState>(initState)
    val stateScreen: LiveData<HomeScreenState> = _stateScreen

    fun updateCount(postId: Int, item: StatisticItem) {
        val modifiedList = _stateScreen.getValueNotNull()
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
        _stateScreen.value = modifiedList
    }


    fun removePost(post: FeedPost) {
        val modifiedList = _stateScreen.getValueNotNull()
        modifiedList.remove(post)
        _stateScreen.value = modifiedList
    }

    private fun LiveData<List<FeedPost>>.getValueNotNull(): MutableList<FeedPost> =
        this.value?.toMutableList() ?: mutableListOf()
}