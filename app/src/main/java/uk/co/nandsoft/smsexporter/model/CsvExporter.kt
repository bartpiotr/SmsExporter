package uk.co.nandsoft.smsexporter.model

import android.net.Uri

interface CsvExporter {
    fun toCsv(smsList : List<Sms>) : Uri
}