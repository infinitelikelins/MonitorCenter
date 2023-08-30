package com.bearya.monitor.center.fragment

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bearya.monitor.center.data.bean.BaseCount
import com.bearya.monitor.center.data.bean.RobotLog
import com.bearya.monitor.center.databinding.FragmentCentralControlBinding
import com.bearya.monitor.center.model.CentralControlViewModel
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.orhanobut.logger.Logger
import com.romainpiel.shimmer.Shimmer
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
class CentralControlFragment : Fragment() {

    private lateinit var bindView: FragmentCentralControlBinding
    private val viewModel by viewModels<CentralControlViewModel>()

    private val formatter by lazy { SimpleDateFormat("yyyy-MM-dd HH:mm:ss") }

    private var launch: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bindView = FragmentCentralControlBinding.inflate(inflater, container, false)
        return bindView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBarChart()
        initHorizontalBarChart()
        initPieChart()

    }

    private fun initBarChart() {

        bindView.barChart.setBackgroundColor(Color.TRANSPARENT)
        bindView.barChart.setDrawBorders(false) // 不显示边框
        bindView.barChart.setDrawGridBackground(false) // 不显示图标网格
        bindView.barChart.setDrawBarShadow(false) // 不显示背景阴影
        bindView.barChart.isHighlightFullBarEnabled = false

        //默认显示在顶端，这是设置到底部，符合我们正常视觉
        bindView.barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        //去掉底部图例BarChatView 的提示，大家可以根据自己业务需求，对Legend进行定制
        bindView.barChart.legend.isEnabled = false

        bindView.barChart.axisLeft.setDrawZeroLine(false)
        //去掉左侧Y轴刻度
        bindView.barChart.axisLeft.setDrawLabels(false)
        //去掉左侧Y轴
        bindView.barChart.axisLeft.setDrawAxisLine(false)
        //取消X轴
        bindView.barChart.xAxis.setDrawAxisLine(false)
        //去掉中间竖线
        bindView.barChart.xAxis.setDrawGridLines(false)

        bindView.barChart.xAxis.textColor = Color.WHITE
        //去掉中间横线
        bindView.barChart.axisLeft.setDrawGridLines(false)
        //不使用右侧Y轴
        bindView.barChart.axisRight.isEnabled = false
        bindView.barChart.axisLeft.isEnabled = false
        bindView.barChart.description.isEnabled = false

        bindView.barChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return value.toInt().toString()
            }
        }

    }

    private fun initHorizontalBarChart() {

        bindView.horizontalBarChart.setBackgroundColor(Color.TRANSPARENT)
        bindView.horizontalBarChart.setDrawBorders(false) // 不显示边框
        bindView.horizontalBarChart.setDrawGridBackground(false) // 不显示图标网格
        bindView.horizontalBarChart.setDrawBarShadow(false) // 不显示背景阴影
        bindView.horizontalBarChart.isHighlightFullBarEnabled = false
        bindView.horizontalBarChart.setDrawValueAboveBar(true)

        //默认显示在顶端，这是设置到底部，符合我们正常视觉
        bindView.horizontalBarChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        //去掉底部图例BarChatView 的提示，大家可以根据自己业务需求，对Legend进行定制
        bindView.horizontalBarChart.legend.isEnabled = false

        bindView.horizontalBarChart.xAxis.granularity = 1f
        bindView.horizontalBarChart.axisLeft.axisMinimum = 0f
        bindView.horizontalBarChart.axisLeft.axisMaximum = 100f

        bindView.horizontalBarChart.axisLeft.setDrawZeroLine(false)
        //去掉左侧Y轴刻度
        bindView.horizontalBarChart.axisLeft.setDrawLabels(false)
        //去掉左侧Y轴
        bindView.horizontalBarChart.axisLeft.setDrawAxisLine(false)
        //取消X轴
        bindView.horizontalBarChart.xAxis.setDrawAxisLine(false)
        //去掉中间竖线
        bindView.horizontalBarChart.xAxis.setDrawGridLines(false)

        bindView.horizontalBarChart.xAxis.textSize = 14f
        bindView.horizontalBarChart.xAxis.textColor = Color.WHITE
        //去掉中间横线
        bindView.horizontalBarChart.axisLeft.setDrawGridLines(false)
        //不使用右侧Y轴
        bindView.horizontalBarChart.axisLeft.isEnabled = false
        bindView.horizontalBarChart.axisRight.isEnabled = false
        bindView.horizontalBarChart.description.isEnabled = false

    }

    private fun initPieChart() {
        bindView.pieChart.legend.isEnabled = true
        bindView.pieChart.isRotationEnabled = true
        bindView.pieChart.description.isEnabled = false
        bindView.pieChart.isRotationEnabled = false
        bindView.pieChart.transparentCircleRadius = 0f
        bindView.pieChart.isHighlightPerTapEnabled = false
        bindView.pieChart.setDrawEntryLabels(false)
        bindView.pieChart.setBackgroundColor(Color.TRANSPARENT)
        bindView.pieChart.setExtraOffsets(-80f, 5f, 0f, 5f)
        bindView.pieChart.holeRadius = 64f
        bindView.pieChart.setHoleColor(Color.TRANSPARENT)
        bindView.pieChart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            orientation = Legend.LegendOrientation.VERTICAL
            textColor = Color.WHITE
            textSize = 12f
            yOffset = 12f
            formSize = 12f
            formToTextSpace = 10f
            yEntrySpace = 2f
            form = Legend.LegendForm.SQUARE
        }

    }

    private fun generateBarChartData(list: List<BaseCount>?) {

        //xAxisList的长度要和list的长度一直，否则会数组越界
        bindView.barChart.xAxis.setLabelCount(list?.size ?: 0, false)

        val lists = list?.map { BarEntry(it.name.toFloat(), it.num.toFloat()) } ?: emptyList()

        bindView.barChart.data = BarData(BarDataSet(lists, "").apply {
            valueTextSize = 10f
            valueTextColor = Color.WHITE
            color = Color.parseColor("#34FBE6")
            barBorderWidth = 0f
            setDrawValues(false)
        }).apply { barWidth = 0.4f }

        bindView.barChart.invalidate()
    }

    private fun generateHorizontalBarChartData(list: List<BaseCount>?) {

        bindView.horizontalBarChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return list?.get(value.toInt())?.name ?: ""
            }
        }
        //xAxisList的长度要和list的长度一直，否则会数组越界
        bindView.horizontalBarChart.xAxis.setLabelCount(list?.size ?: 0, false)

        val lists = MutableList(5) { BarEntry(it.toFloat(), list?.get(it)?.num?.toFloat() ?: 0f) }

        bindView.horizontalBarChart.data = BarData(BarDataSet(lists, "").apply {
            valueTextSize = 12f
            valueTextColor = Color.WHITE
            colors = MutableList(5) { _ ->
                SecureRandom.getInstance("SHA1PRNG").let { Color.rgb(it.nextInt(255), it.nextInt(255), it.nextInt(255)) }
            }
            barBorderWidth = 0f
            setDrawValues(true)
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
        }).apply { barWidth = 0.4f }

        bindView.horizontalBarChart.invalidate()
    }

    private fun generatePieChartData(list: List<BaseCount>?) {

        val entries = list?.map { PieEntry(it.num.toFloat(), it.name) }

        bindView.pieChart.data = PieData(PieDataSet(entries, "").apply {
            sliceSpace = 0f
            setDrawValues(false)
            colors = MutableList(5) { _ ->
                SecureRandom.getInstance("SHA1PRNG").let { Color.rgb(it.nextInt(255), it.nextInt(255), it.nextInt(255)) }
            }
        })

        bindView.pieChart.invalidate()
    }

    override fun onStart() {
        super.onStart()

        viewModel.centralControl.observe(viewLifecycleOwner) {

            bindView.partnersCount.text = "${if (it?.partners?.num == 0) 645 else it?.partners?.num}"
            bindView.robotsCount.text = "${it?.deployedRobots?.num}"
            bindView.lessonsCount.text = "${it?.courseSections?.num}"

//            val color = ColorRandom.getRandomColor()
//            bindView.partnersCount.setTextColor(color)
//            bindView.robotsCount.setTextColor(color)
//            bindView.lessonsCount.setTextColor(color)

            generateHorizontalBarChartData(it?.courseSectionList?.list?.reversed())
            generatePieChartData(it?.courseSectionDuration?.list)
            generateBarChartData(it?.courseSectionHour?.list)
            changeDyn(it?.robotLog)
        }

        Shimmer().apply {
            duration = 1500
            startDelay = 300
            direction = Shimmer.ANIMATION_DIRECTION_LTR
            repeatCount = ValueAnimator.INFINITE
        }.start(bindView.partnersCount)

        Shimmer().apply {
            duration = 1500
            startDelay = 300
            direction = Shimmer.ANIMATION_DIRECTION_LTR
            repeatCount = ValueAnimator.INFINITE
        }.start(bindView.robotsCount)

        Shimmer().apply {
            duration = 1500
            startDelay = 300
            direction = Shimmer.ANIMATION_DIRECTION_LTR
            repeatCount = ValueAnimator.INFINITE
        }.start(bindView.lessonsCount)

    }

    private fun changeDyn(list: List<RobotLog>?) {

        launch?.cancel()

        if (list != null && list.size == 1) {

            val robotLog1 = list[0]
            bindView.time1.text = formatter.format(robotLog1.online * 1000)
            bindView.message1.animateText("${robotLog1.title} ${robotLog1.code} ${robotLog1.address}")
            bindView.status1.text = if (robotLog1.status == 0) "在线" else "离线"

        } else if (list != null && list.size >= 2) {

            launch = lifecycleScope.launch {
                var index = 0

                while (true) {
                    Logger.d("$index")
                    val robotLog1 = list[index]
                    bindView.time1.text = formatter.format(robotLog1.online * 1000)
                    bindView.message1.animateText("${robotLog1.title} ${robotLog1.code} ${robotLog1.address}")
                    bindView.status1.text = if (robotLog1.status == 0) "在线" else "离线"

                    val robotLog2 = list[index + 1]
                    bindView.time2.text = formatter.format(robotLog2.online * 1000)
                    bindView.message2.animateText("${robotLog2.title} ${robotLog2.code} ${robotLog2.address}")
                    bindView.status2.text = if (robotLog2.status == 0) "在线" else "离线"

                    if (index == list.size - 2) index = 0 else index++

                    delay(5000)
                }
            }

        }

    }

}