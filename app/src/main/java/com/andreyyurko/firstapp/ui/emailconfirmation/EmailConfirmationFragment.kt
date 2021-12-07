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
import com.andreyyurko.firstapp.ui.base.BaseFragment
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentEmailConfirmationBinding
import com.andreyyurko.firstapp.entity.ProfileInformation
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {


    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)

    private val viewModel: EmailConfirmationViewModel by viewModels()

    private val millisRunning : Long = 10000
    private val countDownInterval : Long= 1000
    private val timer = timer()
    private lateinit var profileInformation : ProfileInformation


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("SignUpProfileInformation") { _, bundle ->
            val response = bundle.get("SignUpProfileInformationBundleKey")
            profileInformation = Gson().fromJson(response.toString(), ProfileInformation::class.java)
        }

        viewBinding.sendAgainClickableText.isEnabled = false
        timer.start()

        viewBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.sendAgainClickableText.setOnClickListener {
            //Toast.makeText(requireContext(), "Send code please!", Toast.LENGTH_SHORT).show()
            viewModel.sendCodeAgain(profileInformation.email)
            //subscribeToSendCodeRespond()

            viewBinding.sendAgainClickableText.isEnabled = false
            timer().start()
        }

        viewBinding.submitButton.setOnClickListener {
            val code = viewBinding.verificationCodePanel.getCode()
            //Toast.makeText(requireContext(), "Submit code: $code!", Toast.LENGTH_SHORT).show()
            viewModel.submitCode(
                firstName = profileInformation.firstName,
                lastName = profileInformation.lastName,
                userName = profileInformation.nickName,
                email = profileInformation.email,
                password = profileInformation.password,
                code = code
            )
            subscribeToVerifyCodeRespond()
        }
    }

    private fun subscribeToSendCodeRespond() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sendingActionStateFlow().collect { response ->
                    when (response) {
                        is EmailConfirmationViewModel.SendingCodeActionState.SendingSuccess-> {
                            // Do nothing (ошибка должна вылететь если не получилось код отправить)
                            // Можно её будет обработать потом
                        }
                        else -> {
                            // Do nothing.
                        }
                    }
                }
            }
        }
    }

    private fun subscribeToVerifyCodeRespond() {
        // Можно тут в последствии добавить иконку загрузки
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.confirmationActionStateFlow().collect { response ->
                    when (response) {
                        is EmailConfirmationViewModel.SubmissionCodeActionState.SubmissionCodeSuccess-> {
                            Toast.makeText(requireContext(), "Done!", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            // Do nothing.
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
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