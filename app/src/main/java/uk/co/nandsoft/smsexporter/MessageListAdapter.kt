package uk.co.nandsoft.smsexporter

import android.content.Context

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uk.co.nandsoft.smsexporter.model.Sms
import kotlinx.android.synthetic.main.message_row.view.*
import java.text.DateFormat.SHORT
import java.text.SimpleDateFormat

class MessageListAdapter(val context: Context) : RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {
    var smsList: List<Sms> = ArrayList<Sms>()

    override fun getItemCount() : Int{
        return smsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = smsList[position]
        holder.bindMessage(message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.message_row, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindMessage(message: Sms) {
            setContent(message)
            setNumber(message)
            setMessageDirection(message)
            setDate(message)
        }

        private fun setContent(message: Sms) {
            itemView.textViewContent.text = message.content
        }

        fun setNumber(message: Sms) {
            if (message.recipient.isEmpty()) {
                itemView.textViewNumber.text = message.sender
            } else {
                itemView.textViewNumber.text = message.recipient
            }
        }

        private fun setMessageDirection(message: Sms){
            if (message.recipient.isEmpty()) {
                itemView.textViewInOut.text = context.getString(R.string.`in`)
            } else {
                itemView.textViewInOut.text = context.getString(R.string.out)
            }
        }

        private fun setDate(message: Sms) {
            val shortDate = SimpleDateFormat.getDateInstance(SHORT).format(message.dateTime)
            itemView.textViewDate.text = shortDate
        }

    }

}