package com.andreyyurko.firstapp.ui.emailconfirmation

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentEmailConfirmationBinding
import com.andreyyurko.firstapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {

    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)

    private val viewModel: EmailConfirmationViewModel by viewModels()

    private val millisRunning : Long = 10000
    private val countDownInterval : Long= 1000
    private val timer = timer()
    private var email : String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.backButton.applyInsetter {
            type(statusBars = true) { margin() }
        }

        setFragmentResultListener("SignUpEmail") { _, bundle ->
            email = bundle.get("SignUpEmailBundleKey").toString()
            email?.let { sendCode()}
        }

        viewBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.sendAgainClickableText.setOnClickListener {
            email?.let { sendCode()}
        }

        viewBinding.submitButton.setOnClickListener {
            val code = viewBinding.verificationCodePanel.getCode()
            email?.let { it -> viewModel.submitCode(it, code) }
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.confirmationActionStateFlow().collect { response ->
                        when (response) {
                            is EmailConfirmationViewModel.ConfirmationCodeActionState.SubmissionCodeSuccess-> {
                                viewModel.refreshSuccess()
                                val controller = findNavController()
                                if (controller.currentDestination == controller.findDestination(R.id.emailConfirmationFragment)){
                                    controller.popBackStack()
                                }
                                Toast.makeText(requireContext(), "Подтверждение кода прошло успешно!", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                //TODO: обработать ошибки
                            }
                        }
                    }
                }
            }
        }
    }

    private fun sendCode() {
        email?.let { viewModel.sendCode(it) }
        viewBinding.sendAgainClickableText.isEnabled = false
        timer.start()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sendingActionStateFlow().collect { response ->
                    when (response) {
                        is EmailConfirmationViewModel.SendingCodeActionState.SendingSuccess-> {
                            // Do nothing
                        }
                        else -> {
                            // TODO: обработать случаи ошибки
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        timer.cancel()
        super.onPause()
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    private fun timer() : CountDownTimer {
        return object: CountDownTimer(millisRunning,countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                viewBinding.sendInformation.isVisible = true
                val secondsUntilFinished = (millisUntilFinished/1000).toString()
                viewBinding.sendInformation.setText("Осталось: $secondsUntilFinished с")
            }

            override fun onFinish() {
                viewBinding.sendAgainClickableText.isEnabled = true
                viewBinding.sendInformation.isVisible = false
            }
        }
    }
}