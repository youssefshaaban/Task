package com.example.taskjobs.ui.base


import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.taskjobs.R


abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(){

    private lateinit var mViewDataBinding: T



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dm = resources.displayMetrics
        val conf = resources.configuration
//        val lang = "ar"
//        conf.setLocale(Locale(lang.toLowerCase())) // API 17+ only.
//       resources.updateConfiguration(conf, dm)

        performDataBinding()

        //added
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        observeViewModel()
    }



    open fun showLoading() {
        hideLoading()
    }


    open fun hideLoading() {

    }


    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewDataBinding.executePendingBindings()

    }




    abstract fun observeViewModel()


    fun getViewDataBinding(): T {
        return mViewDataBinding
    }

    override fun onDestroy() {
        super.onDestroy()
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}