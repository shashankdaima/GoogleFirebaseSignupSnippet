package com.example.loginandfirestone

data class CameraDetails(
    val smilingProbability: Float,
    val leftEyeOpenProbability: Float,
    val rightEyeOpenProbability: Float,
    val eulerAngleX: Double,
    val eulerAngleY: Double,
    val eulerAngleZ: Double,
) {
}