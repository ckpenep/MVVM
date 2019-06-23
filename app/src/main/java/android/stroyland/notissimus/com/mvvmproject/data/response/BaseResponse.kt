package android.stroyland.notissimus.com.mvvmproject.data.response

import com.google.gson.annotations.SerializedName

open class BaseResponse (
    @field:SerializedName("error_message")
    val errorMessage: String? = null
)