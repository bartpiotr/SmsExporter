package uk.co.nandsoft.smsexporter

import android.Manifest
import uk.co.nandsoft.smsexporter.model.CsvExporter
import uk.co.nandsoft.smsexporter.model.SmsRetriever

class ExportPresenterImpl(val view: MainView, val retriever: SmsRetriever, val exporter: CsvExporter) : ExportPresenter {

    private var hasPermissionSMS = false
    private var hasPermissionWriteFile = false

    override fun onCreate() {
        checkPermissions()
        requestPermissions()
        enableExport()
    }

    override fun onPermissionChanged() {
        checkPermissions()
        enableExport()
    }

    override fun onPermissionRequestRejected() {
        view.quit()
    }

    override fun performExport() {
        val smsList = retriever.fetchAll()
        val csvFileUri = exporter.toCsv(smsList)
        view.sendEmail(csvFileUri)
    }

    private fun enableExport(){
        if(hasPermissions()){
            view.enableExport()
        }else{
            view.disableExport()
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