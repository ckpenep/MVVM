package android.stroyland.notissimus.com.mvvmproject.di.components

import android.app.Application
import android.stroyland.notissimus.com.mvvmproject.di.modules.*
import android.stroyland.notissimus.com.mvvmproject.mvvm.BaseViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    RetrofitApiModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class,
    RepositoryModule::class,
    NavigationModule::class
])
interface AppComponent {
    fun plus(activityModule: ActivityModule): ActivityComponent



    fun inject(app: Application)
    fun inject(target: BaseViewModel)
}