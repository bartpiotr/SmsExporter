package uk.co.nandsoft.smsexporter

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyVararg
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uk.co.nandsoft.smsexporter.model.CsvExporter
import uk.co.nandsoft.smsexporter.model.SmsRetriever

class ExportPresenterImplTest{


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
        verify(retriever).fetchAll()
    }

    @Test
    fun exportSendsEmail(){
        presenter.performExport()
        verify(view).sendEmail(anyVararg())
    }

    @Test
    fun exportWritesSmsToCsvFile(){
        presenter.performExport()
        verify(exporter).toCsv(any())
    }
}