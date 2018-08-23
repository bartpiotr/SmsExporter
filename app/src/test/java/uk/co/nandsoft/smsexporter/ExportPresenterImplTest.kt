package uk.co.nandsoft.smsexporter

import com.nhaarman.mockitokotlin2.*
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
        whenever(view.hasPermission(any())).thenReturn(false)
    }

    @Test
    fun oCreate_ChecksPermissions(){
        presenter.onCreate()
        verify(view, times(2)).hasPermission(any())
    }

    @Test
    fun onCreate_RequestsPermissionsIfNeeded(){
        presenter.onCreate()
        verify(view).requestPermissions(any())
    }

    @Test
    fun onPermissionChange_QuitsWhenPermissionsNotGranted(){
        presenter.onCreate()
        presenter.onPermissionChanged()
        verify(view).closeApp()
    }

    @Test
    fun export_SendsEmail(){
        presenter.performExport()
        verify(view).sendEmail(anyVararg())
    }

    @Test
    fun export_WritesSmsToCsvFile(){
        presenter.performExport()
        verify(exporter).toCsv(any())
    }

    @Test
    fun onFilterChanged_RetrievesMessages(){
        presenter.onFilterChanged(true, true)
        verify(retriever).fetchAll(any(), any())
    }
}