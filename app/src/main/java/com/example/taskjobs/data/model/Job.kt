package com.example.taskjobs.data.model

import java.io.Serializable

class Job(val id:String,
          val type:String,
          val url:String,
          val created_at:String,
          val company:String,
          val company_url:String,
          val location:String,
          val title:String,

          val description:String,

          val how_to_apply:String,
          val company_logo:String,
          var isSelect:Boolean=false
          ):Serializable {
    override fun equals(other: Any?): Boolean {
        return  (other is Job &&other.id==this.id)
    }
}