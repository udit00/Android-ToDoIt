package com.udit.todoit.ui.login.model

data class LoginModel(
    val UserID: Int,
    val Name: String,
    val Password: String,
    val DisplayPicture: String?, // Use nullable String for nullable fields
    val CreatedOn: String,
    val FirebaseToken: String?,
    val EmailID: String?,
    val MobileNo: String,
    val IsActive: Boolean,
    val IsPremium: Boolean
)