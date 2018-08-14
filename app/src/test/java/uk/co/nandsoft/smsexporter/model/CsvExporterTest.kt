package uk.co.nandsoft.smsexporter.model

import android.content.Context
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class CsvExporterTest{
    private val context = mock<Context>()
    private val csvExporter = CsvExporterImpl(context)
    private val smsList =  ArrayList<Sms>()
    private val firstMessage = Sms("0123456789", "0444555666", "First Message", Date(2000L))
    private val secondMessage = Sms("0222333444", "0777888999", "Second Message", Date(4000L))

    @Before
    fun setUp(){
        whenever(context.getString(any())).thenReturn("header")
        smsList.add(firstMessage)
        smsList.add(secondMessage)
    }

    @Test
    fun toCsvData_ReturnsCorrectSizeList() {
        val result = csvExporter.toCsvData(smsList)
        assertEquals(3, result.size)
    }

    @Test
    fun toCsvData_ReturnsCorrectData(){
        val result = csvExporter.toCsvData(smsList)

        assertEquals(firstMessage.sender, result.get(2)[0])
        assertEquals(firstMessage.recipient, result.get(2)[1])
        assertEquals(firstMessage.dateTime.toString(), result.get(2)[2])
        assertEquals(firstMessage.content, result.get(2)[3])
        assertEquals(secondMessage.sender, result.get(1)[0])
        assertEquals(secondMessage.recipient, result.get(1)[1])
        assertEquals(secondMessage.dateTime.toString(), result.get(1)[2])
        assertEquals(secondMessage.content, result.get(1)[3])
    }
}