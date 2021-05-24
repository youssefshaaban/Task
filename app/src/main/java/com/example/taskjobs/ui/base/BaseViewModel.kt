package com.example.taskjobs.ui.base

import androidx.lifecycle.ViewModel



abstract class BaseViewModel : ViewModel() {
    /**Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */
    //val errorManager: ErrorManager = ErrorManager(ErrorMapper())
}
