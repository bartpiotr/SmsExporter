package uk.co.nandsoft.smsexporter.model

import java.time.LocalDateTime
import java.util.*

data class Sms (
        val sender: String,
        val content: String,
        val dateTime: Date
)
