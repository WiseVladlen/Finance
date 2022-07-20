package com.example.finance.presentation.main

import android.util.Log
import androidx.lifecycle.*
import com.example.finance.domain.entities.Stock
import com.example.finance.domain.interactors.LoadRemoteQuoteInteractor
import com.example.finance.domain.interactors.LoadRemoteStockInteractor
import com.example.finance.presentation.FinanceApplication
import com.example.finance.presentation.enums.NetworkAccessType
import com.example.finance.utils.BASE_EXCHANGE
import com.example.finance.utils.CheckInternetConnection.isConnected
import com.example.finance.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel(
    private val loadRemoteQuoteInteractor: LoadRemoteQuoteInteractor,
    private val loadRemoteStockInteractor: LoadRemoteStockInteractor,
) : ViewModel() {

    private var _message: MutableLiveData<Event<NetworkAccessType>> = MutableLiveData()
    val message: LiveData<Event<NetworkAccessType>> get() = _message

    val stockList: MutableLiveData<List<Stock>> by lazy {
        MutableLiveData<List<Stock>>().also {
            if (!isConnected(FinanceApplication.INSTANCE)) {
                _message.value = Event(NetworkAccessType.NOT_AVAILABLE)
            } else {
                viewModelScope.launch(Job() + Dispatchers.IO) {
                    _message.postValue(Event(NetworkAccessType.AVAILABLE))
                    try {
                        val stockSymbols = loadRemoteStockInteractor.getStockList(BASE_EXCHANGE)
                        stockList.postValue(stockSymbols)
                    } catch (exception: Exception) {
                        _message.postValue(Event(NetworkAccessType.ERRORABLE))
                        Log.e(TAG, exception.message.toString())
                    }
                }
            }
        }
    }

    fun getLatestQuote(fromIndex: Int, toIndex: Int) {
        if (!isConnected(FinanceApplication.INSTANCE)) {
            _message.value = Event(NetworkAccessType.NOT_AVAILABLE)
        } else {
            viewModelScope.launch(Job() + Dispatchers.IO) {
                _message.postValue(Event(NetworkAccessType.AVAILABLE))
                try {
                    val quoteList = stockList.value?.let { list ->
                        loadRemoteQuoteInteractor.getQuoteList(
                            list,
                            fromIndex,
                            toIndex,
                        )
                    }
                    stockList.postValue(quoteList)
                } catch (exception: Exception) {
                    _message.postValue(Event(NetworkAccessType.ERRORABLE))
                    Log.e(TAG, exception.message.toString())
                }
            }
        }
    }

    class MainViewModelFactory @Inject constructor(
        private val loadRemoteQuoteInteractor: LoadRemoteQuoteInteractor,
        private val loadRemoteStockInteractor: LoadRemoteStockInteractor,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(
                loadRemoteQuoteInteractor,
                loadRemoteStockInteractor,
            ) as T
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}