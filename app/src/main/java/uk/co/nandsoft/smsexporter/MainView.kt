package uk.co.nandsoft.smsexporter

import android.content.Context
import android.net.Uri

interface MainView{
    fun hasPermission(permission: String): Boolean
    fun requestPermissions(permissions: Array<String>)
    fun sendEmail(attachmentUri: Uri)
    fun showMessage(message: Int)
    fun closeApp()
}