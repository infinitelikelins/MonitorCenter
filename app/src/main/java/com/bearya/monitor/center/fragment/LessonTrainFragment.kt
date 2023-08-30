package com.bearya.monitor.center.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bearya.monitor.center.adapter.LessonTrainAdapter
import com.bearya.monitor.center.data.bean.BaseCount
import com.bearya.monitor.center.databinding.FragmentLessonBinding
import com.bearya.monitor.center.model.CenterViewModel
import com.bearya.monitor.center.model.LessonTrainViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.text.DecimalFormat

class LessonTrainFragment : Fragment() {

    private lateinit var bindView: FragmentLessonBinding
    private val viewModel by viewModels<LessonTrainViewModel>()
    private val adapter by lazy { LessonTrainAdapter() }
    private val centerViewModel by activityViewModels<CenterViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bindView = FragmentLessonBinding.inflate(inflater, container, false)
        return bindView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPieChart(bindView.pieLeftUp)
        initPieChart(bindView.pieLeftDown)
        initBarChart(bindView.barRightUp)
        initBarChart(bindView.barRightDown)

        bindView.trainList.adapter = adapter

        viewModel.currentPageIndex.observe(viewLifecycleOwner) {
            bindView.pageIndex.text = it
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("-Fragment","onResume")
        centerViewModel.onKeyListener = {
            if (it == KeyEvent.KEYCODE_DPAD_DOWN) viewModel.next()
            else if (it == KeyEvent.KEYCODE_DPAD_UP) viewModel.up()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("-Fragment","onPause")
        centerViewModel.onKeyListener = null
    }

    override fun onStart() {
        super.onStart()

        viewModel.lessonTrain.observe(viewLifecycleOwner) {

            generatePieChartData(bindView.pieLeftUp, it?.courseUseToday?.list)
            generatePieChartData(bindView.pieLeftDown, it?.courseUseSum?.list)
            generateBarChartData(bindView.barRightUp, it?.courseUseToday?.list)
            generateBarChartData(bindView.barRightDown, it?.courseUseSum?.list)

        }

        viewModel.trainLog.observe(viewLifecycleOwner) {
            adapter.submitList(it?.list?.reversed())
        }
        bindView.trainList.start()

        lifecycleScope.launch {
            repeat(10000) {
                delay(1000 * 25)
                centerViewModel.onKeyListener?.invoke(KeyEvent.KEYCODE_DPAD_DOWN)
            }
        }

    }

    override fun onStop() {
        super.onStop()
        bindView.trainList.stop()
    }

    private fun initPieChart(chart: PieChart) {
        chart.legend.isEnabled = true
        chart.isRotationEnabled = true
        chart.description.isEnabled = false
        chart.isRotationEnabled = false
        chart.transparentCircleRadius = 0f
        chart.isHighlightPerTapEnabled = false
        chart.setDrawEntryLabels(false)
        chart.setBackgroundColor(Color.TRANSPARENT)
        chart.setExtraOffsets(-40f, 5f, 10f, 5f)
        chart.holeRadius = 64f
        chart.setUsePercentValues(true)
        chart.setHoleColor(Color.TRANSPARENT)
        chart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            orientation = Legend.LegendOrientation.VERTICAL
            textColor = Color.WHITE
            textSize = 10f
            yOffset = 10f
            formSize = 6f
            formToTextSpace = 5f
            yEntrySpace = 2f
            form = Legend.LegendForm.SQUARE
        }

    }

    private fun generatePieChartData(chart: PieChart, list: List<BaseCount>?) {
        val entries = list?.map { PieEntry(it.num.toFloat(), it.name) }

        chart.data = PieData(PieDataSet(entries, "").apply {
            sliceSpace = 0f
            setDrawValues(true)
            colors = MutableList(entries?.size ?: 0) { _ ->
                SecureRandom.getInstance("SHA1PRNG").let { Color.rgb(it.nextInt(255), it.nextInt(255), it.nextInt(255)) }
            }
            valueTextSize = 12f
            setValueTextColors(colors)
            valueLineColor = Color.LTGRAY
            yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            valueLinePart1OffsetPercentage = 60f
            valueLineWidth = 1f
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return "${DecimalFormat("0.##").format(value)}%"
                }
            }
        })

        chart.invalidate()
    }

    private fun initBarChart(chart: BarChart) {

        chart.setBackgroundColor(Color.TRANSPARENT)
        chart.setDrawBorders(false) // 不显示边框
        chart.setDrawGridBackground(false) // 不显示图标网格
        chart.setDrawBarShadow(false) // 不显示背景阴影
        chart.isHighlightFullBarEnabled = false
        chart.setDrawValueAboveBar(true)

        //默认显示在顶端，这是设置到底部，符合我们正常视觉
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        //去掉底部图例BarChatView 的提示，大家可以根据自己业务需求，对Legend进行定制
        chart.legend.isEnabled = false

        chart.xAxis.granularity = 1f
        chart.axisLeft.axisMinimum = 0f

        chart.axisLeft.setDrawZeroLine(false)
        //去掉左侧Y轴刻度
        chart.axisLeft.setDrawLabels(false)
        //去掉左侧Y轴
        chart.axisLeft.setDrawAxisLine(false)
        chart.axisRight.setDrawAxisLine(true)
        //取消X轴
        chart.xAxis.setDrawAxisLine(true)
        //去掉中间竖线
        chart.xAxis.setDrawGridLines(false)

        chart.xAxis.textSize = 14f
        chart.xAxis.textColor = Color.WHITE
        // 去掉中间横线
        // chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.setDrawGridLines(false)
        //不使用右侧Y轴
        chart.axisLeft.isEnabled = false
        chart.axisRight.isEnabled = true
        chart.description.isEnabled = false

    }

    private fun generateBarChartData(chart: BarChart, list: List<BaseCount>?) {

        val lists = list?.mapIndexed { index, baseCount -> BarEntry(index.toFloat(), baseCount.num.toFloat()) }

        chart.axisLeft.axisMaximum = list?.maxBy { it.num }?.num?.plus(50f) ?: 100f

        chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return list?.get(value.toInt())?.name ?: ""
            }
        }
        //xAxisList的长度要和list的长度一直，否则会数组越界
        chart.xAxis.setLabelCount(list?.size ?: 0, false)

        chart.data = BarData(BarDataSet(lists, "").apply {
            valueTextSize = 12f
            valueTextColor = Color.WHITE
            colors = MutableList(lists?.size ?: 0) { _ ->
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

        chart.invalidate()
    }

}