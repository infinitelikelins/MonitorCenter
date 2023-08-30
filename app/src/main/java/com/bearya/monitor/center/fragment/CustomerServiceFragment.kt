package com.bearya.monitor.center.fragment

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bearya.monitor.center.R
import com.bearya.monitor.center.adapter.AfterSalesAdapter
import com.bearya.monitor.center.data.bean.BaseCount
import com.bearya.monitor.center.databinding.FragmentServiceBinding
import com.bearya.monitor.center.model.CustomerServiceViewModel
import com.romainpiel.shimmer.Shimmer

class CustomerServiceFragment : Fragment() {

    private lateinit var bindView: FragmentServiceBinding

    private val viewModel by viewModels<CustomerServiceViewModel>()

    private val adapter by lazy { AfterSalesAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bindView = FragmentServiceBinding.inflate(inflater, container, false)
        return bindView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindView.afterSales.adapter = adapter
    }

    private fun bindQuestion(questions: List<BaseCount>?) {
        bindView.index1.text = spannedString(1, "#A8071A", questions?.get(0))
        bindView.index2.text = spannedString(2, "#D46B08", questions?.get(1))
        bindView.index3.text = spannedString(3, "#fadb14", questions?.get(2))
        bindView.index4.text = spannedString(4, "#7cb305", questions?.get(3))
        bindView.index5.text = spannedString(5, "#1890FF", questions?.get(4))
    }

    private fun spannedString(index: Int, color: String, question: BaseCount?) = buildSpannedString {
        bold {
            color(Color.parseColor(color)) { append("No.${index}  ") }
            color(Color.parseColor("#3ABAF9")) { append(question?.name) }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.customerService.observe(viewLifecycleOwner) { customerService ->

            bindView.serviceRingTimes.text = "${customerService?.serverTimes?.num}"
            bindView.serviceRingPercent.text = "${customerService?.dealRate?.num}"
            bindView.serviceRingEmergency.text = "${customerService?.emergencySupport?.num}"
            bindView.portableCount.text = "${customerService?.portableRobot?.num}"
            bindView.desktopCount.text = "${customerService?.desktopRobot?.num}"

//            val color = ColorRandom.getRandomColor()
//            bindView.portableCount.setTextColor(color)
//            bindView.desktopCount.setTextColor(color)

            val question = customerService?.consultQuestion?.list?.sortedWith { o1, o2 -> o2.num.compareTo(o1.num) }

            bindQuestion(question)

            adapter.submitList(customerService?.servers)

        }

        Shimmer().apply {
            duration = 1500
            startDelay = 300
            direction = Shimmer.ANIMATION_DIRECTION_LTR
            repeatCount = ValueAnimator.INFINITE
        }.start(bindView.portableCount)

        Shimmer().apply {
            duration = 1500
            startDelay = 300
            direction = Shimmer.ANIMATION_DIRECTION_LTR
            repeatCount = ValueAnimator.INFINITE
        }.start(bindView.desktopCount)

    }

    override fun onResume() {
        super.onResume()
        bindView.afterSales.start()

        if(Build.VERSION.SDK_INT < VERSION_CODES.N) {
            bindView.serviceRingTimes.background =
                AnimatedVectorDrawableCompat.create(requireContext() , R.drawable.rotate_ring_2adcac)?.apply { start() }
            bindView.serviceRingPercent.background =
                AnimatedVectorDrawableCompat.create(requireContext() , R.drawable.rotate_ring_d0aa09)?.apply { start() }
            bindView.serviceRingEmergency.background =
                AnimatedVectorDrawableCompat.create(requireContext() , R.drawable.rotate_ring_fc5658)?.apply { start() }
        } else {
            (bindView.serviceRingTimes.background as? Animatable)?.start()
            (bindView.serviceRingPercent.background as? Animatable)?.start()
            (bindView.serviceRingEmergency.background as? Animatable)?.start()
        }

    }

    override fun onPause() {
        super.onPause()
        bindView.afterSales.stop()

        (bindView.serviceRingTimes.background as? Animatable)?.stop()
        (bindView.serviceRingPercent.background as? Animatable)?.stop()
        (bindView.serviceRingEmergency.background as? Animatable)?.stop()

    }

}