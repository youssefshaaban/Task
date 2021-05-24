package com.example.taskjobs.utils

import android.app.Activity
import android.app.Service
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.taskjobs.App
import com.example.taskjobs.utils.SingleEvent
import com.google.android.material.snackbar.Snackbar

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


fun View.showKeyboard() {
    (App.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (App.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun AppCompatActivity.setupActionBar(){
    supportActionBar?.setDisplayShowHomeEnabled(true)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}


/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).run {
        addCallback(object : Snackbar.Callback() {
            override fun onShown(sb: Snackbar?) {

            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            }
        })
        show()
    }
}

fun View.showToast(
    message: String,
    timeLength: Int
) {
    Toast.makeText(this.context, message, timeLength).show()
}


/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<SingleEvent<Any>>,
    timeLength: Int
) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            when (it) {
                is String -> {
                    hideKeyboard()
                    showSnackbar(it, timeLength)
                }
                is Int -> {
                    hideKeyboard()
                    showSnackbar(this.context.getString(it), timeLength)
                }
                else -> {
                }
            }

        }
    })
}

fun View.showToast(
    lifecycleOwner: LifecycleOwner,
    ToastEvent: LiveData<SingleEvent<Any>>,
    timeLength: Int
) {

    ToastEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            when (it) {
                is String -> Toast.makeText(this.context, it, timeLength).show()
                is Int -> Toast.makeText(this.context, this.context.getString(it), timeLength)
                    .show()
                else -> {
                }
            }
        }
    })
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}


fun ImageView.loadImage(url: String?, placeHolder: Int) {
    if (url == null) {
        this.setImageResource(placeHolder)
    } else {
        Glide.with(this.context).load(url).apply(
            RequestOptions()
                .centerCrop()
                .dontAnimate()
                .dontTransform()
                .placeholder(placeHolder)
        ).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable?>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any,
                target: Target<Drawable?>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                //    progressBar.visibility = View.GONE
                return false
            }
        })
            .into(this)
    }
}

fun ImageView.loadImage(url: Uri?, placeHolder: Int) {
    if (url == null) {
        this.setImageResource(placeHolder)
    } else {
        Glide.with(this.context).load(url).apply(
            RequestOptions()
                .centerCrop()
                .dontAnimate()
                .dontTransform()
                .placeholder(placeHolder)
        ).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable?>,
                isFirstResource: Boolean
            ): Boolean {
          //      this@loadImage.setColorFilter(Color.parseColor(PrefColorSettings.getColorAccent()))
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any,
                target: Target<Drawable?>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                //    progressBar.visibility = View.GONE
                return false
            }
        })
            .into(this)
    }
}

fun AppCompatTextView.setTextFutureExt(text: String) =
    setTextFuture(
        PrecomputedTextCompat.getTextFuture(
            text,
            TextViewCompat.getTextMetricsParams(this),
            null
        )
    )

fun AppCompatEditText.setTextFutureExt(text: String) =
    setText(
        PrecomputedTextCompat.create(text, TextViewCompat.getTextMetricsParams(this))
    )
fun AppCompatActivity.startActivityWithFade(intent: Intent){
    this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    this.startActivity(intent)
}

fun AppCompatActivity.startActivityForResultWithFade(intent: Intent, requestCode: Int){
    this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    this.startActivityForResult(intent, requestCode)
}

fun Fragment.startActivityWithFade(intent: Intent){
    this.activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    this.startActivity(intent)
}

fun Fragment.startActivityForResultWithFade(intent: Intent, requestCode: Int){
    this.activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    this.startActivityForResult(intent, requestCode)
}

fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
}

fun showDialogTime(
    context: Context,
    selectionTime: String?,
    timeSelected: TimePickerDialog.OnTimeSetListener
): TimePickerDialog {
    var calendar: Calendar = Calendar.getInstance()
    if (!selectionTime.isNullOrEmpty()) {
        try {
            val date = SimpleDateFormat("HH:mm:ss").parse(selectionTime)
            calendar.time = date!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    val dialog = TimePickerDialog(
        context,
        timeSelected,
        calendar[Calendar.HOUR_OF_DAY],
        calendar[Calendar.MINUTE],
        false
    )
    return dialog
}



