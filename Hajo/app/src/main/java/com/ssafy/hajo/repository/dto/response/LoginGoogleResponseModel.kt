package com.ssafy.hajo.repository.dto.response

import com.google.gson.annotations.SerializedName

data class LoginGoogleResponseModel(
    @SerializedName("access_token") var access_token: String
)
