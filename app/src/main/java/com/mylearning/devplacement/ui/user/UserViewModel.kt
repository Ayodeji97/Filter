package com.mylearning.devplacement.ui.user

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.repository.MainRepository
import com.mylearning.devplacement.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor
    (private val mainRepository: MainRepository,
     @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<User>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<User>>>
        get() = _dataState


    @ExperimentalCoroutinesApi
    fun setStateEvent (mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {

                is MainStateEvent.GetUserEvent -> {
                    mainRepository.getUser()
                        .onEach { dataState ->
                            _dataState.value = dataState

                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }


    sealed class MainStateEvent {

        object GetUserEvent : MainStateEvent()

        object None : MainStateEvent()
    }
}