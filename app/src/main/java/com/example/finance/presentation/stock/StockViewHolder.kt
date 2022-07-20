package com.example.finance.presentation.stock

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.finance.R
import com.example.finance.databinding.StockLayoutBinding
import com.example.finance.domain.entities.Stock
import com.example.finance.presentation.FinanceApplication

class StockViewHolder(private val binding: StockLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("StringFormatMatches", "SetTextI18n")
    fun bind(stock: Stock) {
        val resources = FinanceApplication.INSTANCE.resources
        with(binding) {
            textViewStockCurrencyAndSymbol.text = resources.getString(
                R.string.stock_currency_and_symbol_field,
                stock.currency,
                stock.symbol,
            )
            textViewPrice.text = "${stock.price}"
            textViewPercentChange.text = "${stock.percentChange}%"
            textViewChange.text = resources.getString(
                R.string.change_field,
                stock.change,
            )
            when {
                stock.change > 0 -> {
                    (R.style.GrowStockStyle).let { style ->
                        textViewPercentChange.setTextAppearance(style)
                        textViewChange.setTextAppearance(style)
                        imageViewStockPriceDynamics.setImageResource(R.drawable.arrow_up)
                    }
                }
                stock.change < 0 -> {
                    (R.style.IncreaseStockStyle).let { style ->
                        textViewPercentChange.setTextAppearance(style)
                        textViewChange.setTextAppearance(style)
                        imageViewStockPriceDynamics.setImageResource(R.drawable.arrow_down)
                    }
                }
                else -> {
                    (R.style.StagnationStockStyle).let { style ->
                        textViewPercentChange.setTextAppearance(style)
                        textViewChange.setTextAppearance(style)
                        imageViewStockPriceDynamics.setImageResource(R.drawable.stagnation_icon)
                    }
                }
            }
        }
    }
}