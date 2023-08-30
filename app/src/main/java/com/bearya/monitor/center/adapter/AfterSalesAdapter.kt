package com.bearya.monitor.center.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bearya.monitor.center.R
import com.bearya.monitor.center.data.bean.BaseContent
import com.bearya.monitor.center.databinding.ItemAfterServicesBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import java.text.SimpleDateFormat

class AfterSalesAdapter : BaseQuickAdapter<BaseContent, AfterSalesAdapter.AfterSalesViewHolder>() {

    private val formatter by lazy { SimpleDateFormat("yyyy-MM-dd") }

    inner class AfterSalesViewHolder(val bindViewHolder: ItemAfterServicesBinding) : ViewHolder(bindViewHolder.root)

    override fun onBindViewHolder(holder: AfterSalesViewHolder, position: Int, item: BaseContent?) {
        holder.bindViewHolder.school.text = "${item?.title}"
        holder.bindViewHolder.date.text = formatter.format(item?.dateline?.times(1000))

        when (item?.status) {
            0 -> {
                holder.bindViewHolder.questionsIcon.setImageResource(R.drawable.question_new)
                holder.bindViewHolder.status.text = "新问题"
                holder.bindViewHolder.status.setBackgroundResource(R.drawable.shape_rectangle_f33f4a_corner6)
            }

            1 -> {
                holder.bindViewHolder.questionsIcon.setImageResource(R.drawable.question_handle)
                holder.bindViewHolder.status.text = "处理中"
                holder.bindViewHolder.status.setBackgroundResource(R.drawable.shape_rectangle_ff7f1a_corner6)
            }

            2 -> {
                holder.bindViewHolder.questionsIcon.setImageResource(R.drawable.question_completed)
                holder.bindViewHolder.status.text = "已解决"
                holder.bindViewHolder.status.setBackgroundResource(R.drawable.shape_rectangle_389e0d_corner6)
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): AfterSalesViewHolder {
        return AfterSalesViewHolder(
            ItemAfterServicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

}