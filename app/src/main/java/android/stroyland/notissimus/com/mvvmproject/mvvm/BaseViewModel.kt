package android.stroyland.notissimus.com.mvvmproject.mvvm

import android.app.Application
import android.stroyland.notissimus.com.mvvmproject.MVVMApp
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel(val app: Application): ViewModel() {

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    init {
        MVVMApp.instance.appComponent.inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}