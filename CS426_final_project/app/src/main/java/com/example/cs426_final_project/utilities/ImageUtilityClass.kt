package com.example.cs426_final_project.utilities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Shader
import android.widget.ImageView

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

        fun cropCenter(imageView : ImageView){
            imageView.adjustViewBounds = true
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        }

        fun cropCircleBitmap(originalBitmap: Bitmap?): Bitmap? {
            val circularBitmap = Bitmap.createBitmap(
                originalBitmap!!.width,
                originalBitmap.height,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(circularBitmap)

            val paint = Paint()
            paint.shader = BitmapShader(
                originalBitmap,
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP
            )


            val radius = originalBitmap.width.coerceAtMost(originalBitmap.height) / 2

            canvas.drawCircle(
                (originalBitmap.width / 2).toFloat(),
                (originalBitmap.height / 2).toFloat(), radius.toFloat(), paint
            )

            return circularBitmap
        }



        fun cropSquareBitmap(originalBitmap: Bitmap?): Bitmap? {

            val size = originalBitmap?.width?.coerceAtMost(originalBitmap.height)

            val squareBitmap = size?.let { Bitmap.createBitmap(it, size, Bitmap.Config.ARGB_8888) }

            val canvas = squareBitmap?.let { Canvas(it) }


            val left = (originalBitmap!!.width - size!!) / 2
            val top = (originalBitmap.height - size) / 2


            val srcRect = Rect(left, top, left + size, top + size)
            val destRect = Rect(0, 0, size, size)
            canvas?.drawBitmap(originalBitmap, srcRect, destRect, null)

            return squareBitmap
        }

    }
}