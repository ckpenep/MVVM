package android.stroyland.notissimus.com.mvvmproject.di.modules

import android.stroyland.notissimus.com.mvvmproject.Screens
import android.stroyland.notissimus.com.mvvmproject.di.scopes.ActivityScope
import android.stroyland.notissimus.com.mvvmproject.mvvm.BaseViewModel
import android.stroyland.notissimus.com.mvvmproject.mvvm.cicerone.CiceroneFragmentManager
import android.stroyland.notissimus.com.mvvmproject.presentation.activities.BaseActivity
import android.stroyland.notissimus.com.mvvmproject.presentation.fragments.BaseFragment
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.*

@Module
class ActivityModule (private val containerId: Int, private val baseActivity: BaseActivity<*>) {

    @Provides
    @ActivityScope
    fun provideBaseActivity(): BaseActivity<*> = baseActivity

    @Provides
    @ActivityScope
    fun provideCiceroneAppNavigator(): SupportAppNavigator {
        return object : SupportAppNavigator(baseActivity, containerId) {
            override fun applyCommands(commands: Array<Command>) {
                super.applyCommands(commands)
                baseActivity.supportFragmentManager.executePendingTransactions()
            }
        }
    }

    @Provides
    @ActivityScope
    fun provideFragmentManager(ciceroneNavigator: SupportAppNavigator) = object: CiceroneFragmentManager {

        override fun<P: BaseViewModel> replaceFragment(fragment: BaseFragment<P>){
            ciceroneNavigator.applyCommands(arrayOf(Replace(Screens.FragmentScreen(fragment))))
        }

        override fun<P: BaseViewModel> addFragment(fragment: BaseFragment<P>){
            ciceroneNavigator.applyCommands(arrayOf(Forward(Screens.FragmentScreen(fragment))))
        }

        override fun<P: BaseViewModel> newRootFragment(fragment: BaseFragment<P>){
            ciceroneNavigator.applyCommands(arrayOf(BackTo(null), Replace(Screens.FragmentScreen(fragment))))
        }

        override fun exit(){
            ciceroneNavigator.applyCommands(arrayOf(Back()))
        }
    }
}