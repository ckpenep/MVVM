package android.stroyland.notissimus.com.mvvmproject.presentation.activities

import android.content.Context
import android.os.Bundle
import android.stroyland.notissimus.com.mvvmproject.MVVMApp
import android.stroyland.notissimus.com.mvvmproject.R
import android.stroyland.notissimus.com.mvvmproject.di.components.ActivityComponent
import android.stroyland.notissimus.com.mvvmproject.di.modules.ActivityModule
import android.stroyland.notissimus.com.mvvmproject.mvvm.BaseViewModel
import android.stroyland.notissimus.com.mvvmproject.mvvm.cicerone.BackButtonListener
import android.stroyland.notissimus.com.mvvmproject.mvvm.cicerone.CiceroneFragmentManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

abstract class BaseActivity <V : BaseViewModel>: AppCompatActivity() {

    protected lateinit var viewModel: V

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var activityComponent: ActivityComponent

    @Inject
    lateinit var requests: CompositeDisposable

    @Inject
    lateinit var cicerone: Cicerone<Router>

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var ciceroneNavigator: SupportAppNavigator

    @Inject
    lateinit var fragmentManager: CiceroneFragmentManager

    @LayoutRes
    abstract fun layout(): Int
    abstract fun initialization()
    abstract fun provideViewModel(viewModelFactory: ViewModelProvider.Factory): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFragmentComponent()

        viewModel = provideViewModel(viewModelFactory)

        if (layout() != 0) {
            setContentView(layout())
            initialization()
        }
    }

    fun initFragmentComponent() {
        if (!::activityComponent.isInitialized) {
            activityComponent = MVVMApp.instance.appComponent.plus(ActivityModule(R.id.container, this as BaseActivity<BaseViewModel>))
            activityComponent.inject(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(ciceroneNavigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        var fragment: Fragment? = null
        val fragments = fm.fragments
        for (f in fragments) {
            if (f.isVisible) {
                fragment = f
                break
            }
        }

        if (fragment != null
            && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()) {
        } else {
            fragmentManager.exit()
        }
    }

    fun toggleKeyboard(show: Boolean) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (!show)
            inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
        else
            inputMethodManager.toggleSoftInputFromWindow(window.decorView.windowToken, InputMethodManager.SHOW_FORCED, 0)
    }
}