package com.bearya.monitor.center.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bearya.monitor.center.activity.CenterActivity
import com.bearya.monitor.center.databinding.FragmentCodeBinding
import com.bearya.monitor.center.library.ext.Empty
import com.bearya.monitor.center.library.ext.Fail
import com.bearya.monitor.center.library.ext.Invalid
import com.bearya.monitor.center.library.ext.NoNet
import com.bearya.monitor.center.library.ext.None
import com.bearya.monitor.center.library.ext.Success
import com.bearya.monitor.center.library.ext.VERIFY_RESULT
import com.bearya.monitor.center.model.CodeViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import com.tencent.mmkv.MMKV
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 云验证应用
 */
class CodeFragment : Fragment() {

    private lateinit var bindView: FragmentCodeBinding

    private val viewModel by viewModels<CodeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindView = FragmentCodeBinding.inflate(inflater, container, false)
        return bindView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindView.activate.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            val scale = if (hasFocus) 1.18f else 1f
            ViewCompat.animate(v).scaleX(scale).scaleY(scale).start()
        }
        bindView.activate.setOnClickListener {
            code(bindView.licence.text.toString())
        }
    }

    private fun code(data: String) {
        val hud = KProgressHUD.create(requireContext(), KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("正在验证中").setCancellable(false).setAutoDismiss(true).setAnimationSpeed(2)
            .show()
        viewModel.activateVerify(data).observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                delay(1000)
                hud.dismiss()
                when (it) {
                    Success -> {
                        Toasty.success(requireContext(), "激活成功", 6000).show()
                        MMKV.defaultMMKV().encode(VERIFY_RESULT, true)
                        withContext(Dispatchers.Main) {
                            CenterActivity.start(requireContext())
                            requireActivity().finish()
                        }
                    }

                    Invalid -> Toasty.warning(requireContext(), "该授权码已失效", 6000).show()
                    None -> Toasty.error(requireContext(), "该授权码不存在", 6000).show()
                    Empty -> Toasty.error(requireContext(), "请确认您输入的授权码", 6000).show()
                    NoNet -> Toasty.error(requireContext(), "请检查您是否已经联网", 6000).show()
                    Fail -> Toasty.error(requireContext(), "验证失败", 6000).show()
                }
            }
        }
    }

}