package android.stroyland.notissimus.com.mvvmproject.presentation.activities.main

import android.app.Application
import android.stroyland.notissimus.com.mvvmproject.mvvm.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(app: Application) : BaseViewModel(app) {

    //val refreshTokenData = MutableLiveData<DataModel<RefreshTokenResponse>>()

    fun refreshToken(){
//        val request = RefreshTokenRequest("")
//        api.refreshIoken(request, {
//            refreshTokenData.postValue(DataModel(true, it))
//        }, { errorMessage, _ ->
//            refreshTokenData.postValue(DataModel(false))
//            showToast(errorMessage)
//        }).call()
    }
}