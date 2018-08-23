package uk.co.nandsoft.smsexporter

import android.net.Uri
import uk.co.nandsoft.smsexporter.model.Sms

interface MainView{
    fun hasPermission(permission: String): Boolean
    fun requestPermissions(permissions: Array<String>)
    fun sendEmail(attachmentUri: Uri)
    fun showMessage(message: Int)
    fun showSmsList(messages : List<Sms>)
    fun closeApp()
}