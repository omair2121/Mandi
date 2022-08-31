package com.secrets.formers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MandiViewModelFactory(private val repo: MandiRepo): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MandiViewModel(repo) as T
    }
}