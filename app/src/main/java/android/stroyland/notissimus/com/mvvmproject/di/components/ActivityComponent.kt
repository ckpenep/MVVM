package android.stroyland.notissimus.com.mvvmproject.di.components

import android.stroyland.notissimus.com.mvvmproject.di.modules.ActivityModule
import android.stroyland.notissimus.com.mvvmproject.di.scopes.ActivityScope
import android.stroyland.notissimus.com.mvvmproject.mvvm.BaseViewModel
import android.stroyland.notissimus.com.mvvmproject.presentation.activities.BaseActivity
import android.stroyland.notissimus.com.mvvmproject.presentation.activities.main.MainActivityViewModel
import android.stroyland.notissimus.com.mvvmproject.presentation.fragments.BaseFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(target: BaseActivity<BaseViewModel>)
    fun inject(target: BaseFragment<BaseViewModel>)

    fun inject(target: MainActivityViewModel)
}