package com.example.loginandfirestone.facedetector

import android.content.Context
import android.os.Build.VERSION_CODES
import android.preference.PreferenceManager
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import com.example.loginandfirestone.R
import com.google.common.base.Preconditions
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceDetectorOptions.PERFORMANCE_MODE_FAST

object PreferenceUtils {
    fun isCameraLiveViewportEnabled(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_camera_live_viewport)
        return sharedPreferences.getBoolean(prefKey, false)
    }

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    fun getCameraXTargetResolution(context: Context, lensfacing: Int): Size? {
        Preconditions.checkArgument(
            lensfacing == CameraSelector.LENS_FACING_BACK
                    || lensfacing == CameraSelector.LENS_FACING_FRONT
        )
        val prefKey =
            if (lensfacing == CameraSelector.LENS_FACING_BACK) context.getString(R.string.pref_key_camerax_rear_camera_target_resolution) else context.getString(
                R.string.pref_key_camerax_front_camera_target_resolution
            )
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return try {
            Size.parseSize(sharedPreferences.getString(prefKey, null))
        } catch (e: Exception) {
            null
        }
    }

    fun getFaceDetectorOptions(context: Context): FaceDetectorOptions {
        val optionsBuilder = FaceDetectorOptions.Builder()
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setPerformanceMode(PERFORMANCE_MODE_FAST)
            .setMinFaceSize(0.1f)
            .enableTracking()
        return optionsBuilder.build()
    }

    fun shouldHideDetectionInfo(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_info_hide)
        return sharedPreferences.getBoolean(prefKey, false)
    }
}