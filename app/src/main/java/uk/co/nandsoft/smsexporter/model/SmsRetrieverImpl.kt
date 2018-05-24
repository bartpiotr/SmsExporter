package uk.co.nandsoft.smsexporter.model

import android.content.Context
import android.database.Cursor
import android.net.Uri
import java.util.*
const val URI_INBOX = "content://sms/inbox"
const val URI_OUTBOX = "content://sms/outbox"

class SmsRetrieverImpl constructor(private val context: Context) : SmsRetriever {

    override fun fetchAll(): List<Sms> {
        val cursorInbox = getContentCursor(URI_INBOX)
        return toSmsList(cursorInbox)
    }

    private fun toSmsList(cursor: Cursor): ArrayList<Sms> {
        val smsList = ArrayList<Sms>()
        while (cursor.moveToNext()) {
            smsList.add(getSms(cursor))
        }
        return smsList
    }

    private fun getSms(cursor: Cursor): Sms {
        val sender = cursor.getString(cursor.getColumnIndex("address"))
        val content = cursor.getString(cursor.getColumnIndex("body"))
        val dateTime = Date(cursor.getLong(cursor.getColumnIndex("date")))

        return Sms(sender, content, dateTime)
    }

    private fun getContentCursor(contentUri : String): Cursor {
        return context.contentResolver.query(Uri.parse(contentUri), null, null, null, null)
    }

}