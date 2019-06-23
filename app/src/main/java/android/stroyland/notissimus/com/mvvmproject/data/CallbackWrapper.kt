package android.stroyland.notissimus.com.mvvmproject.data

import android.content.Context
import android.stroyland.notissimus.com.mvvmproject.data.response.BaseResponse
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.lang.ref.WeakReference

class CallbackWrapper <T, V>(
    private val contextWeakReference: WeakReference<Context>? = null,
    private val retrofit: Retrofit,
    private var onSuccessResponse:(response: V) -> Unit,
    private var onError:(errorMessage: String, errorCode: Int) -> Unit) : DisposableSingleObserver<T>(){

    companion object {
        const val ERROR_INTERNET_CONNECTION_CODE = 600
        const val ERROR_CONVERTING_DATA_CODE = 601
        const val ERROR_UNEXPECTED_CODE = 666
    }

    var request: Single<T>? = null
    private var repeatingUnauthorizedRequestCount = 0

    override fun onSuccess(response: T) {

        onSuccessResponse(response as V)

//        if (response is BaseResponse) {
//            val data = response as BaseResponse
//            if (!data.response) {
//                when (data.error.code) {
////                    403 -> {
////                        val refreshToken = PaperIO.getRefreshToken()
////                        JoyApi().refreshToken(refreshToken, { res ->
////                            PaperIO.setBearerToken(res.data.bearerToken)
////                            PaperIO.setRefreshToken(res.data.refreshToken)
////                            repeatingUnauthorizedRequestCount++
////                            if (repeatingUnauthorizedRequestCount > 3)
////                                onError(data.error.message, data.error.code)
////                            else
////                                request?.subscribeWith(this)
////                        }, { errorMessage, errorCode ->
////                            onError(errorMessage, errorCode)
////                            contextWeakReference?.get()?.let {
////                                val intent = Intent(it, SuccessActivity::class.java)
////                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
////                                val  pi = PendingIntent.getActivity(it, 0, intent, 0)
////                                pi.send()
////                            }
////                        })
////                    }
//                    else -> onError(data.error.message, data.error.code)
//                }
//            } else onSuccessResponse(response as V)
//        }
//        else if (response is ResponseBody){
//            try {
//                val responseString = response.string()
//                val jsonData = Gson().fromJson<BaseResponse>(responseString)
//                if (jsonData.response) {
//                    onSuccessResponse(responseString as V)
//                }
//                else {
//                    onError(jsonData.error.message, jsonData.error.code)
//                }
//            }
//            catch (e: JSONException){
//                onError(contextWeakReference?.get()?.getString(R.string.error_converting_data)?:"Ошибка преобразования данных", ERROR_CONVERTING_DATA_CODE)
//            }
//        }
    }

    override fun onError(t: Throwable) {
        val errorMessage: String
        val errorCode: Int
        if (t is IOException) {
            errorMessage = /*contextWeakReference?.get()?.getString(R.string.error_no_intenet_connection)?:*/"Проверьте интернет соединение"
            errorCode = ERROR_INTERNET_CONNECTION_CODE
        } else if (t is HttpException) {
            val errorWrapper = ErrorWrapper()
            val error = errorWrapper.parseError(retrofit, t.response())

//            when (error.error.code){
//                401 -> {
//                    val refreshToken = PaperIO.getRefreshToken()
//                    JoyApi().refreshToken(refreshToken, { res ->
//                        PaperIO.setBearerToken(res.data.bearerToken)
//                        PaperIO.setRefreshToken(res.data.refreshToken)
//
//                        repeatingUnauthorizedRequestCount++
//                        if (repeatingUnauthorizedRequestCount > 3) {
//                            repeatingUnauthorizedRequestCount = 0
//                            onError(error.error.message, error.error.code)
//                        }
//                        else{
//                            val callback = CallbackWrapper<T, V>(contextWeakReference, retrofit, onSuccessResponse, onError)
//                            callback.repeatingUnauthorizedRequestCount = repeatingUnauthorizedRequestCount
//                            callback.request = request
//                            request?.subscribeWith(callback)
//                        }
//
//                    }, { errorMessage, errorCode ->
//                        onError(errorMessage, errorCode)
////                        contextWeakReference?.get()?.let {
////                            val intent = Intent(it, SuccessActivity::class.java)
////                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
////                            val  pi = PendingIntent.getActivity(it, 0, intent, 0)
////                            pi.send()
////                        }
//                    })
//
//                    return
//                }
//            }

            if (error != null) {
                errorMessage = error.errorMessage?:""
                errorCode = t.code()
            }
            else {
                errorMessage = /*contextWeakReference?.get()?.getString(R.string.error_unexpected)?:*/"Непредвиденная ошибка"
                errorCode = ERROR_UNEXPECTED_CODE
            }

        } else if (t is IllegalStateException) {
            errorMessage = /*contextWeakReference?.get()?.getString(R.string.error_converting_data)?:*/"Ошибка преобразования данных"
            errorCode = ERROR_CONVERTING_DATA_CODE
        } else {
            errorMessage = /*contextWeakReference?.get()?.getString(R.string.error_unexpected)?:*/"Непредвиденная ошибка"
            errorCode = ERROR_UNEXPECTED_CODE
        }
        onError(errorMessage, errorCode)
    }

    private inner class ErrorWrapper {
        fun parseError(retrofit: Retrofit, response: Response<*>): BaseResponse? {
            val converter: Converter<ResponseBody, BaseResponse> = retrofit
                .responseBodyConverter(BaseResponse::class.java, emptyArray())
            try {
                response.errorBody()?.let {
                    return converter.convert(it)
                }
            } catch (e: Exception) {
                return BaseResponse()
            }
            return BaseResponse()
        }
    }
}