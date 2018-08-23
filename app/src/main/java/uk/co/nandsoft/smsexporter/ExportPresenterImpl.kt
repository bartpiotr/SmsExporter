package uk.co.nandsoft.smsexporter

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import uk.co.nandsoft.smsexporter.model.CsvExporter
import uk.co.nandsoft.smsexporter.model.Sms
import uk.co.nandsoft.smsexporter.model.SmsRetriever

class ExportPresenterImpl(val view: MainView, val retriever: SmsRetriever, val exporter: CsvExporter) : ExportPresenter {

    private var hasPermissionSMS = false
    private var hasPermissionWriteFile = false
    private var smsList : List<Sms> = ArrayList()
    private var fetchInbox = true
    private var fetchOutbox = true

    override fun onCreate() {
        smsList = ArrayList()
        checkPermissions()
        requestPermissions()
    }

    override fun onPermissionChanged() {
        checkPermissions()
        quitWhenNoPermissions()
        fetchSmsList()
        showSmsList()
    }

    override fun performExport() {
        val csvFileUri = exporter.toCsv(smsList)
        view.sendEmail(csvFileUri)
    }

    override fun onFilterChanged(fetchInbox: Boolean, fetchOutbox: Boolean) {
        this.fetchInbox = fetchInbox
        this.fetchOutbox = fetchOutbox
        fetchSmsList()
        showSmsList()
    }

    private fun fetchSmsList() {
        smsList = retriever.fetchAll(fetchInbox, fetchOutbox)
    }

    private fun showSmsList() {
        if(hasPermissions()) {
            view.showSmsList(smsList)
        }
    }

    private fun quitWhenNoPermissions() {
        if (!hasPermissions()) {
            view.showMessage(R.string.permissions_required)
            view.closeApp()
        }
    }

    private fun checkPermissions() {
        hasPermissionSMS = view.hasPermission(Manifest.permission.READ_SMS)
        hasPermissionWriteFile = view.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun requestPermissions(){
        if(hasPermissions()){
            return
        }

        val permissions = ArrayList<String>()

        if(!hasPermissionSMS){
            permissions.add(Manifest.permission.READ_SMS)
        }
        if(!hasPermissionWriteFile){
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        view.requestPermissions(permissions.toTypedArray())
    }

    private fun hasPermissions() = hasPermissionSMS && hasPermissionWriteFile
}