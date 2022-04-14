package com.example.mobileclient

import com.google.gson.annotations.SerializedName


class AccessToken {
    @SerializedName("access_token")
    private var accessToken: String? = null

    @SerializedName("expires_in")
    private var expiresIn: Int? = null

    @SerializedName("refresh_expires_in")
    private var refreshExpiresIn: Int? = null

    @SerializedName("refresh_token")
    private var refreshToken: String? = null

    @SerializedName("token_type")
    private var tokenType: String? = null

    @SerializedName("id_token")
    private var idToken: String? = null

    @SerializedName("not-before-policy")
    private var notBeforePolicy: Int? = null

    @SerializedName("session_state")
    private var sessionState: String? = null

    @SerializedName("scope")
    private var scope: String? = null
}