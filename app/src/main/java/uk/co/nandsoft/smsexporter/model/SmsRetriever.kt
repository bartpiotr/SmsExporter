package uk.co.nandsoft.smsexporter.model

interface SmsRetriever {
    fun fetchAll(): List<Sms>
}