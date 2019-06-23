package android.stroyland.notissimus.com.mvvmproject.mvvm.cicerone

import android.stroyland.notissimus.com.mvvmproject.mvvm.BaseViewModel
import android.stroyland.notissimus.com.mvvmproject.presentation.fragments.BaseFragment

interface CiceroneFragmentManager {
    fun <P: BaseViewModel> replaceFragment(fragment: BaseFragment<P>)

    fun <P: BaseViewModel> addFragment(fragment: BaseFragment<P>)

    fun <P: BaseViewModel> newRootFragment(fragment: BaseFragment<P>)

    fun exit()
}