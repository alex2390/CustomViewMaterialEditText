package com.soubu.customviewmaterialedittext

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by renjiawen on 2021/1/25
 *
 * Desc:
 **/

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = this.toFloat().dp