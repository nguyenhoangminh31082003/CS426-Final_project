package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.view.animation.LinearInterpolator;

import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/**
 * Created by ybq.
 */
public class FoldingCube extends SpriteContainer {

    @SuppressWarnings("FieldCanBeLocal")
    private boolean wrapContent = false;

    @Override
    public Sprite[] onCreateChild() {
        Cube[] cubes
                = new Cube[4];
        for (int i = 0; i < cubes.length; i++) {
            cubes[i] = new Cube();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                cubes[i].setAnimationDelay(300 * i);
            } else {
                cubes[i].setAnimationDelay(300 * i - 1200);
            }
        }
        return cubes;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bounds = clipSquare(bounds);
        int size = Math.min(bounds.width(), bounds.height());
        if (wrapContent) {
            size = (int) Math.sqrt(
                    (size
                            * size) / 2);
            int oW = (bounds.width() - size) / 2;
            int oH = (bounds.height() - size) / 2;
            bounds = new Rect(
                    bounds.left + oW,
                    bounds.top + oH,
                    bounds.right - oW,
                    bounds.bottom - oH
            );
        }

        int px = bounds.left + size / 2 + 1;
        int py = bounds.top + size / 2 + 1;
        for (int i = 0; i < getChildCount(); i++) {
            Sprite sprite = getChildAt(i);
            sprite.setDrawBounds(
                    bounds.left,
                    bounds.top,
                    px,
                    py
            );
            sprite.setPivotX(sprite.getDrawBounds().right);
            sprite.setPivotY(sprite.getDrawBounds().bottom);
        }
    }

    @Override
    public void drawChild(Canvas canvas) {

        Rect bounds = clipSquare(getBounds());
        for (int i = 0; i < getChildCount(); i++) {
            int count = canvas.save();
            canvas.rotate(45 + i * 90, bounds.centerX(), bounds.centerY());
            Sprite sprite = getChildAt(i);
            sprite.draw(canvas);
            canvas.restoreToCount(count);
        }
    }

    private class Cube extends RectSprite {

        Cube() {
            setAlpha(0);
            setRotateX(-180);
        }

        @Override
        public ValueAnimator onCreateAnimation() {
            float fractions[] = new float[]{0f, 0.1f, 0.25f, 0.75f, 0.9f, 1f};
            return new SpriteAnimatorBuilder(this).
                    alpha(fractions, 0, 0, 255, 255, 0, 0).
                    rotateX(fractions, -180, -180, 0, 0, 0, 0).
                    rotateY(fractions, 0, 0, 0, 0, 180, 180).
                    duration(2400).
                    interpolator(new LinearInterpolator())
                    .build();
        }
    }
}
