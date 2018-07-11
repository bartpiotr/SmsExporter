package uk.co.nandsoft.smsexporter.model

import android.content.Context
import android.net.Uri
import com.opencsv.CSVWriter
import uk.co.nandsoft.smsexporter.R
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
        val csvData = ArrayList<Array<String>>()
        addHeader(csvData)
        addContent(smsList, csvData)
        return csvData
    }

    private fun addContent(smsList: List<Sms>, csvData: ArrayList<Array<String>>) {
        smsList.sortedByDescending { sms -> sms.dateTime }
                .forEach { sms -> csvData.add(toStringArray(sms)) }
    }

    private fun addHeader(csvData: ArrayList<Array<String>>) {
        val header = getHeader()
        csvData.add(header)
    }

    private fun getHeader(): Array<String> {
        return arrayOf(
                context.getString(R.string.sender),
                context.getString(R.string.recipient),
                context.getString(R.string.date),
                context.getString(R.string.content)
        )
    }

    private fun toStringArray(sms: Sms)
            = arrayOf(sms.sender, sms.recipient, sms.dateTime.toString(), sms.content)

}