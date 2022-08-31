package com.secrets.formers.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.secrets.formers.data.MandiRepo
import com.secrets.formers.ui.MandiViewModel

class MandiViewModelFactory(private val repo: MandiRepo): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MandiViewModel(repo) as T
    }
}