package com.example.finance.presentation.stock

import androidx.recyclerview.widget.DiffUtil
import com.example.finance.domain.entities.Stock

object StockDiffCallback : DiffUtil.ItemCallback<Stock>() {

    override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
        return (oldItem.currency == newItem.currency)
                && (oldItem.symbol == newItem.symbol)
                && (oldItem.price == newItem.price)
                && (oldItem.change == newItem.change)
                && (oldItem.percentChange == newItem.percentChange)
    }
}