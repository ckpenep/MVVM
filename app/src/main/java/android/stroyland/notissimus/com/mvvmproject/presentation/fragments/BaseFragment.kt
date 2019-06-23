package android.stroyland.notissimus.com.mvvmproject.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.stroyland.notissimus.com.mvvmproject.mvvm.BaseViewModel
import android.stroyland.notissimus.com.mvvmproject.mvvm.cicerone.BackButtonListener
import android.stroyland.notissimus.com.mvvmproject.mvvm.cicerone.CiceroneFragmentManager
import android.stroyland.notissimus.com.mvvmproject.presentation.activities.BaseActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment <V : BaseViewModel>: Fragment(), BackButtonListener {

    protected lateinit var viewModel: V

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    protected lateinit var baseContext: Context

    @Inject
    lateinit var baseActivity: BaseActivity<*>

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var fragmentManager: CiceroneFragmentManager


    protected var rootView: View? = null
    var isVisible: (fragment: Fragment) -> Boolean = { true }


    @LayoutRes
    protected abstract fun layout(): Int
    protected abstract fun initialization(view: View, isFirstInit: Boolean)
    abstract fun provideViewModel(viewModelFactory: ViewModelProvider.Factory): V

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            (context as BaseActivity<*>).activityComponent.inject(this as BaseFragment<BaseViewModel>)
        }
        catch (e: UninitializedPropertyAccessException){
            (context as BaseActivity<*>).initFragmentComponent()
            context.activityComponent.inject(this as BaseFragment<BaseViewModel>)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel(viewModelFactory)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = if (layout() != 0)
            inflater.inflate(layout(), container, false)
        else
            super.onCreateView(inflater, container, savedInstanceState)
        return if (rootView == null) view else rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialization(view, rootView == null)
        rootView = view
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        baseActivity.toggleKeyboard(false)
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onBackPressed(): Boolean {
        fragmentManager.exit()
        return true
    }
}