package uk.co.nandsoft.smsexporter.model

import android.content.Context
import android.net.Uri
import com.opencsv.CSVWriter
import java.io.FileWriter

import java.util.ArrayList

class CsvExporterImpl(val context: Context) : CsvExporter {

    override fun toCsv(smsList : List<Sms>) : Uri{
        val data = toCsvData(smsList)
        val path  = context.externalCacheDir
        val writer = CSVWriter(FileWriter("$path/tempFile.csv"))
        writer.writeAll(data)
        writer.close()

        return Uri.parse("file://$path/tempFile.csv")
    }

    fun toCsvData(smsList: List<Sms>): List<Array<String>> {
        var csvData = ArrayList<Array<String>>()
        csvData.add(arrayOf("Sender", "Recipient", "Date", "Content"))

        smsList.sortedByDescending { sms -> sms.dateTime }
                .forEach { sms -> csvData.add(toStringArray(sms)) }

        return csvData
    }

    private fun toStringArray(sms: Sms)
            = arrayOf(sms.sender, sms.recipient, sms.dateTime.toString(), sms.content)

}