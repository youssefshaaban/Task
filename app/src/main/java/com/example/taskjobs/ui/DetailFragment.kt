package com.example.taskjobs.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskjobs.R
import com.example.taskjobs.data.model.Job
import com.example.taskjobs.databinding.FragmentDetailBinding
import com.example.taskjobs.ui.base.BaseFragment
import com.example.taskjobs.utils.loadImage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=getViewDataBinding()
        val job=arguments?.getSerializable("obj") as Job
        binding.image.loadImage(job.company_logo,R.drawable.bg_no_image)
        binding.companyUrl.setHtml(job.company_url)
        binding.jobUrl.setHtml(job.how_to_apply)
        binding.jobType.setText(job.type)
        binding.description.setHtml(job.description)
        binding.companyName.setText(job
            .company)
        binding.jobTitle.setText(job.title)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_detail
    }

    override fun observeViewModel() {

    }
}