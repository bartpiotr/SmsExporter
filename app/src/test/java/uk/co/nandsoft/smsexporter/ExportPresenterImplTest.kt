package uk.co.nandsoft.smsexporter

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import uk.co.nandsoft.smsexporter.model.CsvExporter
import uk.co.nandsoft.smsexporter.model.SmsRetriever

class ExportPresenterImplTest{

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T

    @Mock
    lateinit var view: MainView

    @Mock
    lateinit var retriever: SmsRetriever

    @Mock
    lateinit var exporter: CsvExporter

    lateinit var presenter: ExportPresenterImpl

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = ExportPresenterImpl(view, retriever, exporter)
    }



    @Test
    fun exportFetchesMessages(){
        presenter.performExport()
        Mockito.verify(retriever).fetchAll()
    }

    @Test
    fun exportSendsEmail(){
        presenter.performExport()
        Mockito.verify(view).sendEmail(any())
    }

    @Test
    fun exportWritesSmsToCsvFile(){
        presenter.performExport()
        Mockito.verify(exporter).toCsv(any())
    }
}