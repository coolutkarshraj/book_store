package com.io.bookstore.model.deliveryPriceModel

data class DeliveryResponseModel(
    var `data`: List<Data>,
    var message: String, // Successfully get all delivery type
    var status: Boolean // true
)