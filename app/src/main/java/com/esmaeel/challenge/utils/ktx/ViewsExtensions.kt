package com.esmaeel.challenge.utils.ktx

import android.view.LayoutInflater
import android.view.View

val View.layoutInflator get() = LayoutInflater.from(this.context)

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}