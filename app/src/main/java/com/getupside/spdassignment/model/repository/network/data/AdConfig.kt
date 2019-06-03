package com.getupside.spdassignment.model.repository.network.data

data class AdConfig(
    val highRiskFlags: List<Any>,
    val safeFlags: List<String>,
    val showsAds: Boolean,
    val unsafeFlags: List<Any>
)