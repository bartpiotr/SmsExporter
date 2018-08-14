package uk.co.nandsoft.smsexporter

interface ExportPresenter {

    fun performExport(fetchInbox: Boolean, fetchOutbox: Boolean)
    fun onCreate()
    fun onPermissionChanged()
}