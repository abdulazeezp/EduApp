package com.example.eduapp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.example.eduapp.R

class SquareButton(context:Context,attrs:AttributeSet):AppCompatButton(context,attrs) {
    override fun setBackgroundResource(resId: Int) {
        super.setBackgroundResource(R.drawable.rectangle_button_background)
    }
}