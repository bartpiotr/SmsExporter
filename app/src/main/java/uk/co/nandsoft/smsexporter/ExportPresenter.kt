package uk.co.nandsoft.smsexporter

import uk.co.nandsoft.smsexporter.model.Sms

interface ExportPresenter {

    fun performExport()
    fun onCreate()
    fun onPermissionChanged()
    fun onFilterChanged(fetchInbox: Boolean, fetchOutbox: Boolean)
}