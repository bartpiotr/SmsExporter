package uk.co.nandsoft.smsexporter.model

import android.content.Context
import org.junit.Assert.*
import org.hamcrest.CoreMatchers.*

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.util.*

class CsvExporterTest{

    @Test
    fun toCsvDataReturnsCollectionOfStringArrays() {
        val context = Mockito.mock(Context::class.java)
        val csvExporter = CsvExporterImpl(context)

        val smsList =  ArrayList<Sms>()
        val firstMessage = Sms("0123456789", "" ,"First Message", Date(2000L))
        val secondMessage = Sms("0222333444", "", "Second Message", Date(4000L))
        smsList.add(firstMessage)
        smsList.add(secondMessage)
        val result = csvExporter.toCsvData(smsList)

        assertEquals(3, result.size)

        assertEquals(firstMessage.sender, result.get(2)[0])
        assertEquals(firstMessage.dateTime.toString(), result.get(2)[2])
        assertEquals(firstMessage.content, result.get(2)[3])

        assertEquals(secondMessage.sender, result.get(1)[0])
        assertEquals(secondMessage.dateTime.toString(), result.get(1)[2])
        assertEquals(secondMessage.content, result.get(1)[3])
    }
}