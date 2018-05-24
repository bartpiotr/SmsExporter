package uk.co.nandsoft.smsexporter.model

import android.content.Context
import android.database.Cursor
import android.net.Uri
import java.util.*
const val URI_INBOX = "content://sms/inbox"
const val URI_OUTBOX = "content://sms/sent"

class SmsRetrieverImpl constructor(private val context: Context) : SmsRetriever {

    override fun fetchAll(): List<Sms> {
        val cursorInbox = getContentCursor(URI_INBOX)
        val cursorOutbox = getContentCursor(URI_OUTBOX)
        val smsList = toSmsList(cursorInbox, true)
        smsList.addAll(toSmsList(cursorOutbox, false))
        return smsList
    }

    private fun toSmsList(cursor: Cursor, isInbox: Boolean): ArrayList<Sms> {
        val smsList = ArrayList<Sms>()
        while (cursor.moveToNext()) {
            smsList.add(getSms(cursor, isInbox))
        }
        return smsList
    }

    private fun getSms(cursor: Cursor, isInbox: Boolean): Sms {
        val address = cursor.getString(cursor.getColumnIndex("address"))
        val content = cursor.getString(cursor.getColumnIndex("body"))
        val dateTime = Date(cursor.getLong(cursor.getColumnIndex("date")))

        if(isInbox){
            return Sms(address, "",  content, dateTime )
        }

        return Sms("", address, content, dateTime)
    }

    private fun getContentCursor(contentUri : String): Cursor {
        return context.contentResolver.query(Uri.parse(contentUri), null, null, null, null)
    }

}