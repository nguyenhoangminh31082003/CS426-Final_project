package com.example.cs426_final_project.contracts

interface CameraContract {
    fun onImageCaptured(bitmapData: ByteArray);
}