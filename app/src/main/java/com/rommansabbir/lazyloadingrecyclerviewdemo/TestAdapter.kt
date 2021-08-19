package com.rommansabbir.lazyloadingrecyclerviewdemo

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rommansabbir.lazyloadingrecyclerviewdemo.databinding.ContentItemTestBinding

class TestAdapter constructor(private val context: Context) :
    RecyclerView.Adapter<TestViewHolder>() {
    fun getContext() = context

    private val diffUtilsCallBack =
        object : DiffUtil.ItemCallback<TestModel>() {
            override fun areItemsTheSame(
                oldItem: TestModel,
                newItem: TestModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: TestModel,
                newItem: TestModel
            ): Boolean {
                return oldItem == newItem
            }
        }

    private var differ: AsyncListDiffer<TestModel> =
        AsyncListDiffer(this, diffUtilsCallBack)

    @SuppressLint("NotifyDataSetChanged")
    fun addDataSet(dataList: MutableList<TestModel>) {
        val previousList =
            ArrayList<TestModel>(differ.currentList.toMutableList())
        previousList.addAll(dataList)
        differ.submitList(previousList)
    }

    fun collectDataSet(): MutableList<TestModel> = differ.currentList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder =
        TestViewHolder(
            ContentItemTestBinding.inflate(
                LayoutInflater.from(getContext()),
                parent,
                false
            ),
            this
        )

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int = collectDataSet().size
}

class TestViewHolder(
    private val binding: ContentItemTestBinding,
    private val adapter: TestAdapter
) : RecyclerView.ViewHolder(binding.root) {
    fun bindView(position: Int) {
        binding.model = adapter.collectDataSet()[position]
        binding.executePendingBindings()
        binding.textView.setOnClickListener {
            Toast.makeText(
                adapter.getContext(),
                "Clicked Item: ${adapter.collectDataSet()[position].value}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}