package com.rommansabbir.lazyloadingrecyclerviewdemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rommansabbir.lazyloadingrecyclerview.LazyLoadingRecyclerView
import com.rommansabbir.lazyloadingrecyclerview.SpeedyLinearLayoutManager
import com.rommansabbir.lazyloadingrecyclerview.attachSpeedyLayoutManager
import com.rommansabbir.lazyloadingrecyclerviewdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity(), LazyLoadingRecyclerView.Listener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var lazyLoadingRecyclerView: LazyLoadingRecyclerView
    private lateinit var adapter: TestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        SpeedyLinearLayoutManager.setMillisPerInch(5f)
        LazyLoadingRecyclerView.setHandlerDelayTime(500)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        //Attach the SpeedyLayoutManager to the RecyclerView first
        binding.rv.attachSpeedyLayoutManager(LinearLayoutManager.VERTICAL, false)

        adapter = TestAdapter(this)
        binding.rv.adapter = adapter

        //Create an instance of LazyLoadingRecyclerView
        lazyLoadingRecyclerView = LazyLoadingRecyclerView.getInstance()

        // Faking data request
        CoroutineScope(Dispatchers.Main).launch {
            binding.pbLoading.visibility = View.VISIBLE
            delay(1000)
            adapter.addDataSet(getRandomList())
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun getRandomList(): MutableList<TestModel> {
        val listToReturn = mutableListOf<TestModel>()
        List(30) { Random.nextInt(0, 100) }.forEach {
            listToReturn.add(TestModel("New Random Int is: $it"))
        }
        return listToReturn
    }

    override fun onResume() {
        super.onResume()
        // IMPORTANT - attach the listener `onResume()` state
        lazyLoadingRecyclerView.registerScrollListener(binding.rv, this)
    }

    override fun onStop() {
        super.onStop()
        // IMPORTANT - remove the listener `onStop` state
        lazyLoadingRecyclerView.removeListener()
    }

    override fun loadMore() {
        // Faking load more data request, infinite scrolling
        CoroutineScope(Dispatchers.Main).launch {
            binding.pbLoadingBottom.visibility = View.VISIBLE
            delay(1000)
            adapter.addDataSet(getRandomList())
            binding.pbLoadingBottom.visibility = View.GONE
        }
    }

    private val listener = object : LazyLoadingRecyclerView.Listener{
        override fun loadMore() {
            // Faking load more data request, infinite scrolling
            CoroutineScope(Dispatchers.Main).launch {
                binding.pbLoadingBottom.visibility = View.VISIBLE
                delay(1000)
                adapter.addDataSet(getRandomList())
                binding.pbLoadingBottom.visibility = View.GONE
            }
        }
    }
}