package com.secrets.formers.data.models

data class VillageModel(
    val villageName: String,
    val price: Float,
    val cropList: List<CropModel>
)
