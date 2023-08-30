package com.bearya.monitor.center.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bearya.monitor.center.data.bean.BaseContent
import com.bearya.monitor.center.databinding.ItemLessonTrainBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import java.text.SimpleDateFormat

class LessonTrainAdapter : BaseQuickAdapter<BaseContent, LessonTrainAdapter.LessonTrainViewHolder>() {

    private val formatter by lazy { SimpleDateFormat("yyyy-MM-dd") }

    override fun onBindViewHolder(holder: LessonTrainViewHolder, position: Int, item: BaseContent?) {
        holder.bindViewHolder.province.text = item?.province
        holder.bindViewHolder.city.text = item?.city
        holder.bindViewHolder.date.text = formatter.format(item?.dateline?.times(1000))
        holder.bindViewHolder.school.text = item?.title
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): LessonTrainViewHolder {
        return LessonTrainViewHolder(
            ItemLessonTrainBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        )
    }

    inner class LessonTrainViewHolder(val bindViewHolder : ItemLessonTrainBinding) : ViewHolder(bindViewHolder.root)

}