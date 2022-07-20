package com.example.finance.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finance.R
import com.example.finance.databinding.ActivityMainBinding
import com.example.finance.presentation.FinanceApplication
import com.example.finance.presentation.enums.NetworkAccessType
import com.example.finance.presentation.interfaces.OnVisiblePositionsChangeListener
import com.example.finance.presentation.stock.StocksAdapter
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModel.MainViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { mainViewModelFactory }
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        FinanceApplication.INSTANCE.appComponent?.inject(this)
        initialize()
    }

    private fun initialize() {
        with(binding) {
            val changeListener = object : OnVisiblePositionsChangeListener {
                override fun onChange(fromIndex: Int, toIndex: Int) {
                    mainViewModel.getLatestQuote(fromIndex, toIndex)
                }
            }
            val stocksAdapter = StocksAdapter(changeListener)
            val linearLayoutManager = LinearLayoutManager(FinanceApplication.INSTANCE)
            val dividerItemDecoration = DividerItemDecoration(
                FinanceApplication.INSTANCE,
                linearLayoutManager.orientation,
            )

            recyclerView.apply {
                layoutManager = linearLayoutManager
                adapter = stocksAdapter
                addItemDecoration(dividerItemDecoration)
            }

            mainViewModel.message.observe(this@MainActivity, { result ->
                result.getContentIfNotHandled()?.let { message ->
                    when (message) {
                        NetworkAccessType.NOT_AVAILABLE -> {
                            showText(FinanceApplication.INSTANCE.getString(R.string.message_no_internet_connection))
                        }
                        NetworkAccessType.AVAILABLE -> {
                            loadingProgressIndicator.visibility = View.VISIBLE
                        }
                        NetworkAccessType.ERRORABLE -> {
                            loadingProgressIndicator.visibility = View.GONE
                            showText(FinanceApplication.INSTANCE.getString(R.string.message_connection_error))
                        }
                    }
                }
            })

            mainViewModel.stockList.observe(this@MainActivity, { list ->
                stocksAdapter.submitList(list)
                recyclerView.visibility = View.VISIBLE
                loadingProgressIndicator.visibility = View.GONE
            })
        }
    }

    private fun showText(text: String) {
        Toast.makeText(FinanceApplication.INSTANCE, text, Toast.LENGTH_SHORT).show()
    }
}