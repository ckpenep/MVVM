package android.stroyland.notissimus.com.mvvmproject.data.repository.login

import android.content.Context
import android.stroyland.notissimus.com.mvvmproject.MVVMApp
import android.stroyland.notissimus.com.mvvmproject.domain.repository.LoginRepository
import retrofit2.Retrofit
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(val context: Context, val retrofit: Retrofit): LoginRepository {

    @Inject
    lateinit var loginApiInterface: LoginApiInterface

    init {
        MVVMApp.instance.appComponent.inject(this)
    }


}