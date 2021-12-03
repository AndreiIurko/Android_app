package com.andreyyurko.firstapp.ui.emailconfirmation

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.ui.base.BaseFragment
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentEmailConfirmationBinding

class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {


    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)

    private val viewModel: EmailConfirmationViewModel by viewModels()

    private val millisRunning : Long = 10000
    private val countDownInterval : Long= 1000
    private val timer = timer()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.sendAgainClickableText.isEnabled = false
        timer.start()
        viewBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.sendAgainClickableText.setOnClickListener {
            Toast.makeText(requireContext(), "Send code please!", Toast.LENGTH_SHORT).show()
            viewBinding.sendAgainClickableText.isEnabled = false
            timer().start()
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