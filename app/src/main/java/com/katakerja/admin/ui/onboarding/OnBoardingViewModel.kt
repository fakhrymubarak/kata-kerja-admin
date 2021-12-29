package com.katakerja.admin.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.katakerja.admin.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(userUseCase: UserUseCase) : ViewModel() {
    val getUserId: LiveData<Int> = userUseCase.getUserId().asLiveData()
}