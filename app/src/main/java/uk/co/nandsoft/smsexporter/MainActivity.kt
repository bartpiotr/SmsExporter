package uk.co.nandsoft.smsexporter

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import de.cketti.mailto.EmailIntentBuilder
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import uk.co.nandsoft.smsexporter.model.CsvExporterImpl
import uk.co.nandsoft.smsexporter.model.Sms
import uk.co.nandsoft.smsexporter.model.SmsRetrieverImpl
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {

    private val REQUEST_CODE_PERMISSIONS = 1
    private val presenter = ExportPresenterImpl(this, SmsRetrieverImpl(this), CsvExporterImpl(this))
    private lateinit var adapter: MessageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        initializeRecyclerView()
        presenter.onCreate()
        presenter.onFilterChanged(checkBoxInbox.isChecked, checkBoxOutbox.isChecked)
    }


    override fun requestPermissions(permissions: Array<String>){
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSIONS)
    }

    override fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun showSmsList(messages: List<Sms>) {
        adapter.smsList = messages
        adapter.notifyDataSetChanged()
    }

    override fun sendEmail(attachmentUri : Uri){
        val emailIntent = EmailIntentBuilder
                .from(this)
                .subject(getString(R.string.sms_list) + Date())
                .build().putExtra(Intent.EXTRA_STREAM, attachmentUri)

        startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email)))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
         if(requestCode == REQUEST_CODE_PERMISSIONS ) {
             presenter.onPermissionChanged()
         }
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun showMessage(message: Int) {
        Toast.makeText(this.applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun closeApp() {
        this.finishAffinity()
    }

    private fun initializeRecyclerView(){
        adapter = MessageListAdapter(this)
        recyclerViewMessages.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun initializeViews() {
        setSupportActionBar(toolbar)
        initializeListeners()
        recyclerViewMessages.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun initializeListeners() {
        buttonExport.setOnClickListener {
            presenter.performExport()
        }

        checkBoxInbox.setOnCheckedChangeListener { buttonView, isChecked ->
            presenter.onFilterChanged(isChecked, checkBoxOutbox.isChecked)
        }

        checkBoxOutbox.setOnCheckedChangeListener { buttonView, isChecked ->
            presenter.onFilterChanged(checkBoxInbox.isChecked, isChecked)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
