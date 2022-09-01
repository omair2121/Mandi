package com.secrets.formers.data

import com.secrets.formers.data.models.SellerModel
import com.secrets.formers.data.models.VillageModel

class MandiRepo {


    fun getVillageList() = mutableListOf<VillageModel>().apply {
        add(VillageModel("Jyotinagar", 0.77652f))
        add(VillageModel("Ramnagar", 120.08f))
        add(VillageModel("Neemgaon", 8.50f))
        add(VillageModel("Jhodge", 81.60f))
        add(VillageModel("Piplegaon", 5.90f))
        add(VillageModel("Kusumbe", 11.00f))
        add(VillageModel("Ravalgaon", 31.10f))
    }

    fun getSellerList() = mutableListOf<SellerModel>().apply {
        add(SellerModel("Raju", "R1000", 1.12f))
        add(SellerModel("Ramu", "R2000", 1.00f))
        add(SellerModel("Sourabh", "R3000", 0.99f))
        add(SellerModel("Vijesh", "R4000", 0.99f))
        add(SellerModel("Nitesh", "R5000", 1.10f))
        add(SellerModel("Sunil", "R6000", 1.02f))
        add(SellerModel("Kaka", "R7000", 1.01f))
        add(SellerModel("Renu", "R8000", 1.05f))
        add(SellerModel("Swapnil", "R9000", 1.09f))
        add(SellerModel("Devre", "S1000", 1.07f))
        add(SellerModel("Kulhare", "S2000", 1.01f))
    }

}