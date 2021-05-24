package com.example.taskjobs.ui.jobFragment

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskjobs.R
import com.example.taskjobs.data.model.Job
import com.example.taskjobs.databinding.ItemLayoutJobcompanyBinding
import com.example.taskjobs.utils.loadImage


class JobAdapter(
    val list: List<Job>,
    val clickDetail: (Job) -> Unit,
    val clickStar: (Job) -> Unit
) : RecyclerView.Adapter<JobAdapter.SingleRow>() {

    inner class SingleRow(private val binding: ItemLayoutJobcompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val job = list[pos]
            binding.jobTitle.text=job.title
            binding.jobTitle.text=job
                .company
            binding.image.loadImage(job.company_logo,R.drawable.bg_no_image)
            if (job.isSelect){
                binding.imageView.setImageResource(R.drawable.ic_active_star)
            }else{
                binding.imageView.setImageResource(R.drawable.ic_un_active_star)
            }
            binding.imageView.setOnClickListener {
                clickStar(job)
            }

            binding.root.setOnClickListener { clickDetail(job) }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JobAdapter.SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout_jobcompany,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: JobAdapter.SingleRow, position: Int) {
        holder.bind(position)
    }


}