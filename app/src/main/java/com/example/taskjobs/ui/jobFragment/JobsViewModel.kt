package com.example.taskjobs.ui.jobFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskjobs.data.Resource
import com.example.taskjobs.data.model.Job
import com.example.taskjobs.data.model.Jobdb
import com.example.taskjobs.data.repositery.JobDataSourceReopRepositery
import com.example.taskjobs.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JobsViewModel(private val jobDataSourceReopRepositery: JobDataSourceReopRepositery) :
    BaseViewModel() {
    private val jobsLiveDataPrivate = MutableLiveData<Resource<List<Job>>>()
    val jobsLiveData: LiveData<Resource<List<Job>>> get() = jobsLiveDataPrivate
    var list = ArrayList<String>()
    val notifyDataChange=MutableLiveData<Boolean>()

//    fun getJobs() {
//        viewModelScope.launch {
//            jobsLiveDataPrivate.value = Resource.Loading()
//
//        }
//    }

    fun  addfavourite(job:Job){
       viewModelScope.launch {
           val jobDb=Jobdb(job.id,job.title,job.company)
           jobDataSourceReopRepositery.addFavourite(jobDb)
           list.add(job.id)
           for (jobb in jobsLiveDataPrivate.value?.data!!){
               if (jobb.id==job.id){
                   jobb.isSelect=true
                   break
               }
           }
           notifyDataChange.value=true
       }
    }

    fun removeFavourite(job:Job){
        val jobDb=Jobdb(job.id,job.title,job.company)
        viewModelScope.launch {
            jobDataSourceReopRepositery.deleteFavourite(jobDb)
            list.remove(job.id)
            for (jobb in jobsLiveDataPrivate.value?.data!!){
                if (jobb.id==job.id){
                    jobb.isSelect=false
                    break
                }
            }
            notifyDataChange.value=true
        }
    }
    fun getJobs() {
        viewModelScope.launch {
            list = ArrayList(jobDataSourceReopRepositery.getJobsIdDb())
            jobDataSourceReopRepositery.getJobs().collect {
                if (it.data != null) {
                    if (!list.isEmpty())
                        it.data.forEach { job ->
                            if (list.contains(job.id)) {
                                job.isSelect = true
                            }
                        }
                    jobsLiveDataPrivate.value = Resource.Success(it.data)
                } else if (it is Resource.DataError) {
                    jobsLiveDataPrivate.value = Resource.DataError(it.error, it.errorCode)
                }
            }
        }
    }

}