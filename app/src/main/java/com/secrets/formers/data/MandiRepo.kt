package com.secrets.formers.data

import com.secrets.formers.data.models.SellerModel
import com.secrets.formers.data.models.VillageModel

class MandiRepo {


    fun getVillageList() = mutableListOf<VillageModel>().apply {
        add(VillageModel("Jyotinagar", 0.77f))
        add(VillageModel("Ramnagar", 120.08f))
        add(VillageModel("Neemgaon", 80.50f))
        add(VillageModel("Jhodge", 101.60f))
        add(VillageModel("Piplegaon", 105.90f))
        add(VillageModel("Kusumbe", 119.00f))
        add(VillageModel("Ravalgaon", 111.10f))
    }

    fun getSellerList() = mutableListOf<SellerModel>().apply {
        add(SellerModel("Raju", "R1350", 1.12f))
        add(SellerModel("Ramu", "R2033", 1.00f))
        add(SellerModel("Sourabh", "S2343", 0.99f))
        add(SellerModel("Vijesh", "R8853", 0.99f))
        add(SellerModel("Nitesh", "R9113", 1.10f))
        add(SellerModel("Sunil", "R2565", 1.02f))
        add(SellerModel("Kaka", "R1625", 1.01f))
        add(SellerModel("Renu", "R7335", 1.05f))
        add(SellerModel("Swapnil", "R5315", 1.09f))
        add(SellerModel("Devre", "R1319", 1.07f))
        add(SellerModel("Kulhare", "K1615", 1.01f))
    }

}