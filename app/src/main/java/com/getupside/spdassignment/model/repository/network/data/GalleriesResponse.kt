package com.getupside.spdassignment.model.repository.network.data

data class GalleriesResponse(
    val `data`: List<Data>,
    val status: Int,
    val success: Boolean
)