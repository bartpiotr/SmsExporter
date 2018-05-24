package uk.co.nandsoft.smsexporter.model

import java.util.*

data class Sms (
        val sender: String,
        val recipient: String,
        val content: String,
        val dateTime: Date
)
