package com.rommansabbir.lazyloadingrecyclerview

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import java.util.*

var handlerDelayTimer: Timer = Timer()

inline fun handlerPostDelayed(delay: Long, crossinline onSuccess: () -> Unit) {
    handlerDelayTimer.cancel()
    handlerDelayTimer = Timer()
    handlerDelayTimer.schedule(object : TimerTask() {
        override fun run() {
            Handler(Looper.getMainLooper()).post {
                onSuccess.invoke()
            }
        }
    }, delay)
}

fun RecyclerView.attachSpeedyLayoutManager(orientation: Int, reverseLayout: Boolean) {
    this.layoutManager = SpeedyLinearLayoutManager(this.context, orientation, reverseLayout)
}