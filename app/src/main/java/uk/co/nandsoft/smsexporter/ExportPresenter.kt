package uk.co.nandsoft.smsexporter

interface ExportPresenter {

    fun performExport()
    fun onCreate()
    fun onPermissionChanged()
    fun onPermissionRequestRejected()
}