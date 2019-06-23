package android.stroyland.notissimus.com.mvvmproject.di.modules

import android.content.Context
import android.stroyland.notissimus.com.mvvmproject.data.repository.login.LoginRepositoryImpl
import android.stroyland.notissimus.com.mvvmproject.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [AppModule::class, RetrofitApiModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginApi(context: Context, retrofit: Retrofit): LoginRepository =
        LoginRepositoryImpl(context, retrofit)
}