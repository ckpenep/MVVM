package android.stroyland.notissimus.com.mvvmproject.di.modules

import android.stroyland.notissimus.com.mvvmproject.di.map_key.ViewModelKey
import android.stroyland.notissimus.com.mvvmproject.di.scopes.ActivityScope
import android.stroyland.notissimus.com.mvvmproject.presentation.activities.main.MainActivityViewModel
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @ActivityScope
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(model: MainActivityViewModel): ViewModel
}