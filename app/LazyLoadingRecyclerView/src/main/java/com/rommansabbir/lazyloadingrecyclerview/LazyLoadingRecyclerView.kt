package com.rommansabbir.lazyloadingrecyclerview

import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView

class LazyLoadingRecyclerView private constructor() {
    private var isScrolling = false
    private var currentItems = 0
    private var totalItems: Int = 0
    private var scrollOutItems: Int = 0

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: SpeedyLinearLayoutManager

    private var loadMoreListener: Listener? = null

    companion object {
        fun getInstance(): LazyLoadingRecyclerView {
            return LazyLoadingRecyclerView()
        }

        private var HANDLER_DELAY: Long = 500

        /**
         * Set delay time for handler, must be in Millis
         *
         * @param value, Value to be set in [Long]
         */
        fun setHandlerDelayTime(value: Long) {
            HANDLER_DELAY = value
        }
    }


    fun registerScrollListener(recyclerView: RecyclerView, listener: Listener) {
        this.recyclerView = recyclerView
        if (recyclerView.layoutManager == null) {
            throw RuntimeException("LazyLoadingRecyclerView: Layout manager can not be null")
        }
        if (recyclerView.layoutManager!! is SpeedyLinearLayoutManager) {
            this.layoutManager = recyclerView.layoutManager!! as SpeedyLinearLayoutManager
            this.loadMoreListener = listener
            recyclerView.addOnScrollListener(rvScrollListener)
        } else {
            throw RuntimeException("LazyLoadingRecyclerView: SpeedyLinearLayoutManager must be attached to the RecyclerView")
        }
    }

    fun removeListener() {
        recyclerView.removeOnScrollListener(rvScrollListener)
        this.loadMoreListener = null
    }

    private val rvScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            handlerPostDelayed(HANDLER_DELAY) {
                currentItems = layoutManager.childCount
                totalItems = layoutManager.itemCount
                scrollOutItems = layoutManager.findFirstVisibleItemPosition()
                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false
                    loadMoreListener?.loadMore()
                }
            }
        }
    }

    interface Listener {
        fun loadMore()
    }
}