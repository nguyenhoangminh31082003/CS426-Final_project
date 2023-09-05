package com.example.cs426_final_project.utilities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix

class ImageUtilityClass {

    companion object {
        fun cropRotateBitmap(bitmapData: ByteArray): Bitmap? {
            var bitmap = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.size)
            val width = bitmap.width
            val height = bitmap.height
            val newSize = width.coerceAtMost(height)
            val cropWidth = (width - newSize) / 2
            val cropHeight = (height - newSize) / 2
            bitmap = Bitmap.createBitmap(bitmap, cropWidth, cropHeight, newSize, newSize)
            val matrix = Matrix()
            matrix.postRotate(-90f)
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            return bitmap
        }
    }
}