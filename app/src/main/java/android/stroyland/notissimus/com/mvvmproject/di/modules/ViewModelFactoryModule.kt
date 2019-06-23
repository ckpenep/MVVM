package android.stroyland.notissimus.com.mvvmproject.di.modules

import android.stroyland.notissimus.com.mvvmproject.di.scopes.ActivityScope
import android.stroyland.notissimus.com.mvvmproject.mvvm.DaggerViewModelFactory
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    @ActivityScope
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}