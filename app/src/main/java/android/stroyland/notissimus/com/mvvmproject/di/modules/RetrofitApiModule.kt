package android.stroyland.notissimus.com.mvvmproject.di.modules

import android.app.Application
import android.stroyland.notissimus.com.mvvmproject.BuildConfig
import android.stroyland.notissimus.com.mvvmproject.data.repository.login.LoginApiInterface
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitApiModule {
    companion object {
        private const val CONNECTION_TIMEOUT_SEC = 30L
        private const val CACHE_SIZE = 10_000_000L
        private const val CACHE_FILE_NAME = "http_cache"
    }

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10L * 1024 * 1024
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }


    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder().apply {
            cache(cache)

            val cookieManager = CookieManager()
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
            cookieJar(JavaNetCookieJar(cookieManager))

            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(httpLoggingInterceptor)
            }

            connectTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
            readTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
            writeTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)

            addInterceptor { chain ->
                val request = chain.request()

                val change = request.newBuilder()
                    .header("Content-Type", "application/json")

                val response = chain.proceed(change.build())

                response
            }
        }.build()
    }

    private fun bodyToString(request: RequestBody?): String {
        try {
            val buffer = Buffer()
            if (request != null)
                request.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginApiImplementation(retrofit: Retrofit): LoginApiInterface {
        return retrofit.create(LoginApiInterface::class.java)
    }
}