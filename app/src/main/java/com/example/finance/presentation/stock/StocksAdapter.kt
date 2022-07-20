package com.example.finance.presentation.stock

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finance.databinding.StockLayoutBinding
import com.example.finance.domain.entities.Stock
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finance.presentation.interfaces.OnVisiblePositionsChangeListener

class StocksAdapter(
    private val changeListener: OnVisiblePositionsChangeListener,
) : ListAdapter<Stock, StockViewHolder>(StockDiffCallback) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StockViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = StockLayoutBinding.inflate(layoutInflater, viewGroup, false)
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: StockViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.bind(item)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        var startIndex: Int = -1
        var endIndex: Int = -1
        val manager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstPos = manager.findFirstCompletelyVisibleItemPosition()
                val lastPos = manager.findLastCompletelyVisibleItemPosition()
                if (startIndex == -1 || endIndex == -1) {
                    startIndex = firstPos
                    endIndex = lastPos + REQUESTED_LOAD_SIZE
                    changeListener.onChange(startIndex, endIndex)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val firstPos = manager.findFirstCompletelyVisibleItemPosition()
                    val lastPos = manager.findLastCompletelyVisibleItemPosition()
                    if (firstPos !in (startIndex + 1) until endIndex
                        && lastPos !in (startIndex + 1) until endIndex
                    ) {
                        startIndex = when (firstPos - REQUESTED_LOAD_SIZE >= 0) {
                            true -> firstPos - REQUESTED_LOAD_SIZE
                            false -> 0
                        }
                        endIndex = when (lastPos + REQUESTED_LOAD_SIZE < currentList.size) {
                            true -> lastPos + REQUESTED_LOAD_SIZE
                            false -> currentList.size - 1
                        }
                        changeListener.onChange(startIndex, endIndex)
                    } else if (firstPos in (startIndex + 1) until endIndex
                        && lastPos !in (startIndex + 1) until endIndex
                    ) {
                        startIndex = endIndex
                        endIndex = when (lastPos + REQUESTED_LOAD_SIZE < currentList.size) {
                            true -> lastPos + REQUESTED_LOAD_SIZE
                            false -> currentList.size - 1
                        }
                        changeListener.onChange(startIndex, endIndex)
                    } else if (firstPos !in (startIndex + 1) until endIndex
                        && lastPos in (startIndex + 1) until endIndex
                    ) {
                        endIndex = startIndex
                        startIndex = when (firstPos - REQUESTED_LOAD_SIZE >= 0) {
                            true -> firstPos - REQUESTED_LOAD_SIZE
                            false -> 0
                        }
                        changeListener.onChange(startIndex, endIndex)
                    }
                }
            }
        })
    }

    companion object {
        private const val REQUESTED_LOAD_SIZE = 5
    }
}