package android.stroyland.notissimus.com.mvvmproject

import android.app.Application
import android.stroyland.notissimus.com.mvvmproject.di.components.AppComponent
import android.stroyland.notissimus.com.mvvmproject.di.components.DaggerAppComponent
import android.stroyland.notissimus.com.mvvmproject.di.modules.AppModule
import android.stroyland.notissimus.com.mvvmproject.di.modules.RetrofitApiModule

class MVVMApp: Application() {

    companion object{
        lateinit var instance: MVVMApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val appComponent: AppComponent by lazy {
        initDagger()
    }

    private fun initDagger(): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .retrofitApiModule(RetrofitApiModule())
            .build()
}