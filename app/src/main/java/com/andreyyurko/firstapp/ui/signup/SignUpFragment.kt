package com.andreyyurko.firstapp.ui.signup

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.CheckBox
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentSignUpBinding
import com.andreyyurko.firstapp.entity.ProfileInformation
import com.andreyyurko.firstapp.ui.base.BaseFragment
import com.andreyyurko.firstapp.util.getSpannedString
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val viewModel: SignUpViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackButtonPressed()
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToEvents()
        viewBinding.backButton.setOnClickListener {
            onBackButtonPressed()
        }
        viewBinding.signUpButton.setOnClickListener {
            viewModel.sendVerificationCode(
                email = viewBinding.emailEditText.text?.toString() ?: ""
            )
        }
        subscribeToFormFields()

        viewBinding.termsAndConditionsCheckBox.setClubRulesText {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/terms")))
        }
    }

    private fun subscribeToFormFields() {
        decideSignUpButtonEnabledState(
            firstname = viewBinding.firstnameEditText.text?.toString(),
            lastname = viewBinding.lastnameEditText.text?.toString(),
            nickname = viewBinding.nicknameEditText.text?.toString(),
            email = viewBinding.emailEditText.text?.toString(),
            password = viewBinding.passwordEditText.text?.toString(),
            termsIsChecked = viewBinding.termsAndConditionsCheckBox.isChecked
        )
        val watcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                decideSignUpButtonEnabledState(
                    firstname = viewBinding.firstnameEditText.text?.toString(),
                    lastname = viewBinding.lastnameEditText.text?.toString(),
                    nickname = viewBinding.nicknameEditText.text?.toString(),
                    email = viewBinding.emailEditText.text?.toString(),
                    password = viewBinding.passwordEditText.text?.toString(),
                    termsIsChecked = viewBinding.termsAndConditionsCheckBox.isChecked
                )
            }
        }
        viewBinding.firstnameEditText.addTextChangedListener(watcher)
        viewBinding.lastnameEditText.addTextChangedListener(watcher)
        viewBinding.nicknameEditText.addTextChangedListener(watcher)
        viewBinding.emailEditText.addTextChangedListener(watcher)
        viewBinding.passwordEditText.addTextChangedListener(watcher)
        viewBinding.termsAndConditionsCheckBox.setOnCheckedChangeListener { _, isChecked ->
            decideSignUpButtonEnabledState(
                firstname = viewBinding.firstnameEditText.text?.toString(),
                lastname = viewBinding.lastnameEditText.text?.toString(),
                nickname = viewBinding.nicknameEditText.text?.toString(),
                email = viewBinding.emailEditText.text?.toString(),
                password = viewBinding.passwordEditText.text?.toString(),
                termsIsChecked = isChecked
            )
        }
    }

    private fun decideSignUpButtonEnabledState(
        firstname: String?,
        lastname: String?,
        nickname: String?,
        email: String?,
        password: String?,
        termsIsChecked: Boolean
    ) {
        viewBinding.signUpButton.isEnabled = !firstname.isNullOrBlank()
                && !lastname.isNullOrBlank()
                && !nickname.isNullOrBlank()
                && !email.isNullOrBlank()
                && !password.isNullOrBlank()
                && termsIsChecked
    }

    private fun onBackButtonPressed() {
        val firstname = viewBinding.firstnameEditText.text?.toString()
        val lastname = viewBinding.lastnameEditText.text?.toString()
        val nickname = viewBinding.nicknameEditText.text?.toString()
        val email = viewBinding.emailEditText.text?.toString()
        val password = viewBinding.passwordEditText.text?.toString()
        if (firstname.isNullOrBlank()
            && lastname.isNullOrBlank()
            && nickname.isNullOrBlank()
            && email.isNullOrBlank()
            && password.isNullOrBlank()
        ) {
            findNavController().popBackStack()
            return
        }
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.common_back_alert_dialog_text)
            .setNegativeButton(R.string.common_back_alert_dialog_cancel_button_text) { dialog, _ ->
                dialog?.dismiss()
            }
            .setPositiveButton(R.string.common_back_alert_dialog_ok_button_text) { _, _ ->
                findNavController().popBackStack()
            }
            .show()
    }

    private fun subscribeToEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signUpActionStateFlow().collect { event ->
                    when (event) {
                        is SignUpViewModel.SignUpActionState.SendVerificationCodeSucess-> {
                            // TODO: можно сделать проверку на непустоту, но кажется кнопка за это отвечает
                            setFragmentResult(
                                "SignUpProfileInformation", bundleOf("SignUpProfileInformationBundleKey" to Gson().toJson(
                                    ProfileInformation(
                                    firstName = viewBinding.firstnameEditText.text.toString(),
                                    lastName = viewBinding.lastnameEditText.text.toString(),
                                    nickName = viewBinding.nicknameEditText.text.toString(),
                                    email = viewBinding.emailEditText.text.toString(),
                                    password = viewBinding.passwordEditText.text.toString()
                                )
                                )
                                )
                            )
                            val controller = findNavController()
                            if (controller.currentDestination == controller.findDestination(R.id.signUpFragment)){
                                controller.navigate(R.id.emailConfirmationFragment)
                            }
                            //findNavController().navigate(R.id.emailConfirmationFragment)

                        }
                        else -> {
                            // Do nothing.
                        }
                    }
                }
            }
        }
    }

    private fun CheckBox.setClubRulesText(
        clubRulesClickListener: () -> Unit
    ) {

        // Turn on ClickableSpan.
        movementMethod = LinkMovementMethod.getInstance()

        val clubRulesClickSpan =
            object : ClickableSpan() {
                override fun onClick(widget: View) = clubRulesClickListener()
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = resources.getColor(R.color.purple_200, null)
                }
            }

        text =
            resources.getSpannedString(
                R.string.sign_up_terms_and_conditions_template,
                buildSpannedString {
                    inSpans(clubRulesClickSpan) {
                        append(resources.getSpannedString(R.string.sign_up_club_rules))
                    }
                }
            )
    }
}


