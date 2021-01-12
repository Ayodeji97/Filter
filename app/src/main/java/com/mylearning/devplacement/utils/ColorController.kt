package com.mylearning.devplacement.utils

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.core.view.setMargins
import com.mylearning.devplacement.R
import com.mylearning.devplacement.model.User

fun LinearLayout.setColors(item: User?) {

    item?.let { element ->
        if (element.colors.isEmpty()) {
            val colors = listOf(
                "Green",
                "Violet",
                "Yellow",
                "Blue",
                "Teal",
                "Maroon",
                "Red",
                "Aquamarine",
                "Orange",
                "Mauv",
                "Puce",
                "Indigo",
                "Turquoise",
                "Goldenrod",
                "Pink",
                "Fuscia",
                "Crimson",
                "Khaki"
            )
            colors.map { color ->
                val childLayout = buildChildLayout()
                val view = buildAllColor(color)
                childLayout.addView(view)
                addView(childLayout)
            }
        } else {
            element.colors.map { color ->
                val childLayout = buildChildLayout()
                val view = buildColor(color)
                childLayout.addView(view)
                addView(childLayout)
            }

        }
    }
}


fun LinearLayout.setCountries(item: User?) {
    item?.let { element ->
        if (element.countries.isEmpty()) {
            val childLayout = buildChildLayout()
            val view = buildTextView("All countries")
            childLayout.addView(view)
            addView(childLayout)
        } else {
            element.countries.map { country ->
                val childLayout = buildChildLayout()
                val view = buildTextView(country)
                childLayout.addView(view)
                addView(childLayout)
            }
        }
    }
}


private fun LinearLayout.buildColor(color: String): TextView {
    val view = TextView(this.context)
    val layoutParams = LinearLayout.LayoutParams(64, 64)
    layoutParams.setMargins(8)
    view.layoutParams = layoutParams
    val drawable = ContextCompat.getDrawable(context, R.drawable.loading_dialog_box)
    drawable?.mutate()?.setColorFilter(
        when (color) {
            Colors.RED.color -> ContextCompat.getColor(context, R.color.red)
            Colors.GREEN.color -> ContextCompat.getColor(context, R.color.green)
            Colors.VIOLET.color -> ContextCompat.getColor(context, R.color.violet)
            Colors.YELLOW.color -> ContextCompat.getColor(context, R.color.yellow)
            Colors.BLUE.color -> ContextCompat.getColor(context, R.color.blue)
            Colors.TEAL.color -> ContextCompat.getColor(context, R.color.teal)
            Colors.MAROON.color -> ContextCompat.getColor(context, R.color.maroon)
            Colors.AQUAMARINE.color -> ContextCompat.getColor(
                context,
                R.color.aquamarine
            )
            Colors.ORANGE.color -> ContextCompat.getColor(context, R.color.orange)
            Colors.MAUV.color -> ContextCompat.getColor(context, R.color.mauv)
            Colors.PUCE.color -> ContextCompat.getColor(context, R.color.puce)
            Colors.INDIGO.color -> ContextCompat.getColor(context, R.color.indigo)
            Colors.TURQUOISE.color -> ContextCompat.getColor(context, R.color.turquoise)
            Colors.GOLDENROD.color -> ContextCompat.getColor(context, R.color.goldenrod)
            Colors.FUSCIA.color -> ContextCompat.getColor(context, R.color.fushcia)
            Colors.PINK.color -> ContextCompat.getColor(context, R.color.pink)
            Colors.CRIMSON.color -> ContextCompat.getColor(context, R.color.crimson)
            Colors.KHAKI.color -> ContextCompat.getColor(context, R.color.khaki)
            else -> ContextCompat.getColor(context, R.color.black_100)
        }, PorterDuff.Mode.SRC_IN
    )

    view.background = drawable
    return view
}

private fun LinearLayout.buildAllColor(color: String): TextView {
    val view = TextView(this.context)
    val layoutParams = LinearLayout.LayoutParams(64, 64)
    layoutParams.setMargins(8)
    view.layoutParams = layoutParams
    val drawable = ContextCompat.getDrawable(context, R.drawable.loading_dialog_box)
    drawable?.mutate()?.setColorFilter(
        when (color) {
            Colors.RED.color -> ContextCompat.getColor(context, R.color.red)
            Colors.GREEN.color -> ContextCompat.getColor(context, R.color.green)
            Colors.VIOLET.color -> ContextCompat.getColor(context, R.color.violet)
            Colors.YELLOW.color -> ContextCompat.getColor(context, R.color.yellow)
            Colors.BLUE.color -> ContextCompat.getColor(context, R.color.blue)
            Colors.TEAL.color -> ContextCompat.getColor(context, R.color.teal)
            Colors.MAROON.color -> ContextCompat.getColor(context, R.color.maroon)
            Colors.AQUAMARINE.color -> ContextCompat.getColor(
                context,
                R.color.aquamarine
            )
            Colors.ORANGE.color -> ContextCompat.getColor(context, R.color.orange)
            Colors.MAUV.color -> ContextCompat.getColor(context, R.color.mauv)
            Colors.PUCE.color -> ContextCompat.getColor(context, R.color.puce)
            Colors.INDIGO.color -> ContextCompat.getColor(context, R.color.indigo)
            Colors.TURQUOISE.color -> ContextCompat.getColor(context, R.color.turquoise)
            Colors.GOLDENROD.color -> ContextCompat.getColor(context, R.color.goldenrod)
            Colors.FUSCIA.color -> ContextCompat.getColor(context, R.color.fushcia)
            Colors.PINK.color -> ContextCompat.getColor(context, R.color.pink)
            Colors.CRIMSON.color -> ContextCompat.getColor(context, R.color.crimson)
            Colors.KHAKI.color -> ContextCompat.getColor(context, R.color.khaki)
            else -> ContextCompat.getColor(context, R.color.black_100)
        }, PorterDuff.Mode.SRC_IN
    )

    view.background = drawable
    return view
}

private fun LinearLayout.buildChildLayout(): LinearLayout {
    val childLayout = LinearLayout(context)
    val linearParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    childLayout.layoutParams = linearParams
    childLayout.gravity = Gravity.CENTER
    return childLayout
}

private fun LinearLayout.buildTextView(country: String): TextView {
    val view = TextView(this.context)
    val layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    view.text = country
    view.setTextColor(ContextCompat.getColor(context, R.color.black))
    view.setPadding(32, 16, 32, 16)
    layoutParams.setMargins(8)
    view.layoutParams = layoutParams
    val drawable = ContextCompat.getDrawable(context, R.drawable.country_bg)
    view.background = drawable
    return view
}