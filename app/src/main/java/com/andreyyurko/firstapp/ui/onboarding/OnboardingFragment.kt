package com.andreyyurko.firstapp.ui.onboarding

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentOnboardingBinding
import com.andreyyurko.firstapp.ui.base.BaseFragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlin.math.abs


class OnboardingFragment : BaseFragment(R.layout.fragment_onboarding) {

    private val viewBinding by viewBinding(FragmentOnboardingBinding::bind)

    private var player: ExoPlayer? = null
    private var isVolumeOn: Boolean = true

    private var page: Int = 0
    private var isScroll = false
    private var timerIsStop = false
    private var millisRunning: Long = 2000
    private var countDownInterval: Long = 100

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player = SimpleExoPlayer.Builder(requireContext()).build().apply {
            addMediaItem(MediaItem.fromUri("asset:///onboarding.mp4"))
            repeatMode = Player.REPEAT_MODE_ALL
            prepare()
        }
        viewBinding.playerView.player = player

        viewBinding.volumeControlButton.setOnClickListener {
            changeVolume()
        }

        viewBinding.viewPager.setTextPages()
        viewBinding.viewPager.attachDots(viewBinding.onboardingTextTabLayout)
        viewBinding.viewPager.offscreenPageLimit = 1
        val nextItemVisiblePx = 250
        val currentItemHorizontalMarginPx = 150
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.30f * abs(position))
            // If you want a fading effect uncomment the next line:
            page.alpha = 0.25f + (1 - abs(position))
        }
        viewBinding.viewPager.setPageTransformer(pageTransformer)

// The ItemDecoration gives the current (centered) item horizontal margin so that
// it doesn't occupy the whole screen width. Without it the items overlap
        val itemDecoration = HorizontalMarginItemDecoration(
            200
        )
        viewBinding.viewPager.addItemDecoration(itemDecoration)

        viewBinding.signInButton.setOnClickListener {
            // TODO: go to SignInFragment
            Toast.makeText(requireContext(), "Нажата кнопка войти", Toast.LENGTH_SHORT).show()
        }
        viewBinding.signUpButton.setOnClickListener {
            // TODO: go to SignUpFragment
            Toast.makeText(requireContext(), "Нажата кнопка зарегистрироваться", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        autoscroll()
        player?.play()
    }

    override fun onPause() {
        super.onPause()
        timerIsStop = true
        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        timerIsStop = true
        player?.release()
    }

    private fun ViewPager2.setTextPages() {
        adapter =
            ListDelegationAdapter(onboardingTextAdapterDelegate()).apply {
                items =
                    listOf(
                        getString(R.string.onboarding_view_pager_text_1),
                        getString(R.string.onboarding_view_pager_text_2),
                        getString(R.string.onboarding_view_pager_text_3)
                    )
            }
    }

    private fun ViewPager2.attachDots(tabLayout: TabLayout) {
        TabLayoutMediator(tabLayout, this) { _, _ -> }.attach()
    }

    private fun changeVolume() {
        if (isVolumeOn) {
            player?.volume = 0F
            isVolumeOn = false
            viewBinding.volumeControlButton.setImageResource(R.drawable.ic_volume_up_white_24dp)
        }
        else {
            player?.volume = 1F
            isVolumeOn = true
            viewBinding.volumeControlButton.setImageResource(R.drawable.ic_volume_off_white_24dp)
        }
    }

    private fun timer() : CountDownTimer {
        return object: CountDownTimer(millisRunning,countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                if (isScroll) {
                    cancel()
                    isScroll = false
                    timer().start()
                }
                if (timerIsStop) {
                    cancel()
                }
            }

            override fun onFinish() {
                if (viewBinding.viewPager.adapter?.itemCount == page + 1) {
                    page = 0
                } else {
                    page++
                }
                viewBinding.viewPager.setCurrentItem(page, true)
                timer().start()
            }
        }
    }

    private fun autoscroll() {
        timerIsStop = false
        timer().start()
        viewBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                isScroll = true
                page = position
            }
        })
    }
}
