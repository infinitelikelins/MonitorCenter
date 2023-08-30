package com.bearya.monitor.center.library.tool

import android.graphics.Color
import java.security.SecureRandom

object ColorRandom {

    private val colors = listOf(
        "#ADC6FF", "#85A5FF", "#597EF7", "#2F54EB", "#1D39C4",
        "#D3ADF7", "#B37FEB", "#9254DE", "#722ED1", "#531DAB",
        "#FFADD2", "#FF85C0", "#F759AB", "#EB2F96", "#C41D7F",
        "#91D5FF", "#69C0FF", "#40A9FF", "#1890FF", "#096DD9",
        "#B7EB8F", "#B7EB8F", "#73D13D", "#52C41A", "#389E0D",
        "#237804", "#FFE58F", "#FFD666", "#FFC53D", "#FAAD14",
        "#D48806", "#AD6800", "#fffb8f", "#fff566", "#ffec3d",
        "#fadb14", "#d4b106", "#ad8b00", "#eaff8f", "#d3f261",
        "#bae637", "#a0d911", "#7cb305", "#5b8c00", "#FFD591",
        "#FFC069", "#FFA940", "#FF7f1A", "#FA8C16", "#D46B08",
        "#AD4E00", "#ffbb96", "#ff9c6e", "#ff7a45", "#fa541c",
        "#FF4D4F", "#F5222D", "#F33F4A", "#CF1322", "#A8071A",
        "#d4380d", "#ad2102", "#FF7875", "#FFF017", "#F9BF1A",
        "#87E8DE", "#5CDBD3", "#36CFC9", "#13C2C2", "#099FA4",
    )

    fun getRandomColor() = Color.parseColor(colors[SecureRandom.getInstance("SHA1PRNG").nextInt(colors.size - 1)])

}