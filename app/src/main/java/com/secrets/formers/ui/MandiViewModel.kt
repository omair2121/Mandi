package com.secrets.formers.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.secrets.formers.data.MandiRepo
import com.secrets.formers.data.models.SellerModel
import com.secrets.formers.data.models.VillageModel

class MandiViewModel(private val repo: MandiRepo): ViewModel() {

// -------------------------------village info-------------------------------------- //

    private var _selectedVillage = MutableLiveData<VillageModel?>()
    val selectedVillage: LiveData<VillageModel?>
        get() = _selectedVillage

    private val villageList by lazy { repo.getVillageList() }

    /**
     * it extracts village name from the model and provides a list,
     * because ArrayAdapter only takes string list
     */
    fun getVillagesName() = mutableListOf<String>().apply {villageList.mapTo(this) { it.villageName } }


    /**
     * this fun provides villageMode from the list which we got from repo
     * @param village is the query which finds the village by villageName
     */
    fun fetchInfoVillageByName(village: String) {
        _selectedVillage.value = villageList.find { it.villageName.equals(village, true)}
    }

// -------------------------------seller info-------------------------------------- //

    private var _selectedSeller = MutableLiveData<SellerModel?>()
    val selectedSeller: LiveData<SellerModel?>
        get() = _selectedSeller

    private val sellerList by lazy { repo.getSellerList() }

    /**
     * this fun provides sellerModel from the list which we got from repo
     * @param sellerName is the query which finds the sellerModel by sellerName
     * observed by @param selectedSeller
     */
    fun fetchInfoByName(sellerName: String) {
       _selectedSeller.value = sellerList.find { it.sellerName.equals(sellerName, ignoreCase = true) }
    }

    /**
     * this fun provides sellerModel from the list which we got from repo
     * @param loyaltyId is the query which finds the sellerModel by loyaltyId of the seller
     */
    fun fetchInfoById(loyaltyId: String) {
       _selectedSeller.value = sellerList.find { it.loyaltyId.equals(loyaltyId, true) }
    }

    fun getSellersNames() = mutableListOf<String>().apply {sellerList.mapTo(this) { it.sellerName } }

    fun getSellersLoyaltyIds() = mutableListOf<String>().apply {sellerList.mapTo(this) { it.loyaltyId } }

    /**
     * reset the selected village and seller values
     */
    fun resetValues() {
        _selectedSeller.value = null
        _selectedVillage.value = null
    }

}