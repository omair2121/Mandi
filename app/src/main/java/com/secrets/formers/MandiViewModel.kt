package com.secrets.formers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MandiViewModel(private val repo: MandiRepo): ViewModel() {
    // village info
    private var _selectedVillage = MutableLiveData<VillageModel?>()
    val selectedVillage: LiveData<VillageModel?>
        get() = _selectedVillage

    private val villageList = repo.getVillageList()


    fun fetchInfoVillageByName(village: String) {
        _selectedVillage.value = villageList.find { it.villageName.equals(village, true)}
    }

    // seller info
    private var _selectedSeller = MutableLiveData<SellerModel?>()
    val selectedSeller: LiveData<SellerModel?>
        get() = _selectedSeller

    private val sellerList = repo.getSellerList()

    fun fetchInfoByName(sellerName: String) {
       _selectedSeller.value = sellerList.find { it.sellerName.equals(sellerName, ignoreCase = true) }
    }

    fun fetchInfoById(loyaltyId: String) {
       _selectedSeller.value = sellerList.find { it.loyaltyId.equals(loyaltyId, true) }
    }

}