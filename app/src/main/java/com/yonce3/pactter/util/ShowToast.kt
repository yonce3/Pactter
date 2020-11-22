package com.yonce3.pactter.util

import android.content.Context
import android.widget.Toast

class ShowToast {
    fun show(context: Context, stringId: Int, showLength: Int) {
        Toast.makeText(context, stringId, showLength).show()
    }
}