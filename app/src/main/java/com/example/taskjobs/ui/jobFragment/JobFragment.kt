package com.example.taskjobs.ui.jobFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskjobs.R
import com.example.taskjobs.data.Resource
import com.example.taskjobs.data.model.Job
import com.example.taskjobs.databinding.FragmentJobBinding
import com.example.taskjobs.ui.base.BaseFragment
import com.example.taskjobs.utils.observe
import com.example.taskjobs.utils.toGone
import com.example.taskjobs.utils.toVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JobFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JobFragment : BaseFragment<FragmentJobBinding>() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentJobBinding
    val viewModel: JobsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewDataBinding()
        binding.rv.layoutManager = LinearLayoutManager(binding.rv.context)
        viewModel.getJobs()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JobFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JobFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_job
    }

    override fun observeViewModel() {
        observe(viewModel.jobsLiveData, ::handleResultJob)
        observe(viewModel.notifyDataChange) {
            if (it) {
                binding.rv.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun handleResultJob(resource: Resource<List<Job>>) {
        when (resource) {
            is Resource.Loading -> {
                binding.progress.toVisible()
            }
            is Resource.Success -> {
                binding.progress.toGone()
                binding.rv.adapter = JobAdapter(resource.data!!, ::handleClickDetail,::handleClickStar)
            }
            is Resource.DataError->{
                binding.progress.toGone()
                showToast(resource.error!!)
            }
        }
    }

    private fun handleClickStar(job: Job) {
        if (job.isSelect){
            viewModel.removeFavourite(job)
        }else{
            viewModel.addfavourite(job)
        }
    }

    private fun handleClickDetail(job: Job) {
        val bundle=Bundle()
        bundle.putSerializable("obj",job)
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_jobFragment_to_detailFragment,bundle)
    }
}
