package com.secrets.formers.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.secrets.formers.CoroutinRule
import com.secrets.formers.data.MandiRepo
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MandiViewModelTest {

    @get:Rule
    var coroutineRule = CoroutinRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MandiViewModel
    @Before
    fun init() {
        viewModel = MandiViewModel(MandiRepo()) // as we are not having db or api calls, hence using same repo
    }

    @Test
    fun `is seller exists`() {
        val findSeller = "Raju"
        viewModel.fetchInfoByName(findSeller)
        val seller = viewModel.selectedSeller.value
        assertThat(seller).isNotNull()
    }

    @Test
    fun `is village exists`() {
        val findVillage = "Jyotinagar"
        viewModel.fetchInfoVillageByName(findVillage)
        val village = viewModel.selectedVillage.value
        assertThat(village).isNotNull()
    }

    @Test
    fun `is loyaltyId exists`() {
        val findSellerById = "R1000"
        viewModel.fetchInfoById(findSellerById)
        val seller = viewModel.selectedSeller.value
        assertThat(seller).isNotNull()
    }
}