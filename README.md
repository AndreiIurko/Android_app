MCSbook
==================

Or your first android application example - social media!

Here is preview:

https://user-images.githubusercontent.com/54847291/218143739-21ba6837-ba03-4c13-8503-ff405341fc2e.mp4

# Features:

Because this is my first project, you can find many basic features: Navigation, XML, Fragments, ViewModels, etc. But some of them were challenging, so I want to highlight them.

- Dependency injection with ![Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
  - Application, fragments and viewModels have annotations
  - ![Here](https://github.com/AndreyYurko/Android_app/blob/main/app/src/main/java/com/andreyyurko/firstapp/repository/AuthRepository.kt) you can find singleton, and ![here](https://github.com/AndreyYurko/Android_app/blob/main/app/src/main/java/com/andreyyurko/firstapp/ui/signin/SignInViewModel.kt) it's injection
- ![Custom view](https://github.com/AndreyYurko/Android_app/blob/main/app/src/main/java/com/andreyyurko/firstapp/ui/emailconfirmation/VerificationCodeEditText.kt) 
- Coroutines and flows:
  - For example ![here](https://github.com/AndreyYurko/Android_app/blob/main/app/src/main/java/com/andreyyurko/firstapp/ui/emailconfirmation/EmailConfirmationViewModel.kt) you can find an implementation of a coroutine that awaits network response and emits state to flow. 
  - ![Here](https://github.com/AndreyYurko/Android_app/blob/main/app/src/main/java/com/andreyyurko/firstapp/ui/MainActivity.kt) is an example of rendering nav graphs based on flow collect.
- Timers: for example, ![here](https://github.com/AndreyYurko/Android_app/blob/main/app/src/main/java/com/andreyyurko/firstapp/ui/onboarding/OnboardingFragment.kt) you can find viewPager with autoscroll. 
- Animation: ![here](https://github.com/AndreyYurko/Android_app/blob/main/app/src/main/java/com/andreyyurko/firstapp/ui/signin/SignInFragment.kt) is my animation with ObjectAnimator.
- Custom RecyclerView divider: ![here](https://github.com/AndreyYurko/Android_app/blob/main/app/src/main/java/com/andreyyurko/firstapp/ui/userlist/CustomDividerItemDecoration.kt) you can find implemetation.
- Mock build: contants in build.app + ![Mock Api](https://github.com/AndreyYurko/Android_app/blob/main/app/src/main/java/com/andreyyurko/firstapp/data/network/MockApi.kt).
- Simple ![implemetation](https://github.com/AndreyYurko/Android_app/blob/main/app/src/main/java/com/andreyyurko/firstapp/data/persistent/LocalKeyValueStorage.kt) of SharedPreferences
