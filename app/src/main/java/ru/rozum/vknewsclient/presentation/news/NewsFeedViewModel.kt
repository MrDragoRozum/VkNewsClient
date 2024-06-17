package ru.rozum.vknewsclient.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.rozum.vknewsclient.data.repository.NewsFeedRepository
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.extensions.mergeWith

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)

    private val recommendationsFlow = repository.recommendations

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("NewsFeedViewModel", "Было поймано исключение")
    }

    private val loadNextDataEvents = MutableSharedFlow<Unit>()
    private val loadNextDataFlow = flow {
        loadNextDataEvents.collect {
            emit(
                NewsFeedScreenState.Posts(
                    posts = recommendationsFlow.value,
                    nextDataIsLoading = true
                )
            )
        }

    }

    val stateScreen = recommendationsFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)


    fun loadNextRecommendations() {
        viewModelScope.launch {
            loadNextDataEvents.emit(Unit)
            repository.loadNextRecommendations()
        }
    }

    fun remove(feedPost: FeedPost) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.deletePost(feedPost)

        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.changeLikeStatus(feedPost)
        }
    }
}