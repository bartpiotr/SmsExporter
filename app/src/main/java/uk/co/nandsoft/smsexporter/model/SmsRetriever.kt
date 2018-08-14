package uk.co.nandsoft.smsexporter.model

interface SmsRetriever {
    fun fetchAll(inbox : Boolean, outbox : Boolean): List<Sms>
}