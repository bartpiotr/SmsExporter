package uk.co.nandsoft.smsexporter

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun array_isCreated(){
        val permissions  = ArrayList<String>()
        permissions.add("Permission One")
        permissions.add("Permission Two")
        var permissionArray = permissions.toTypedArray()
        println(permissionArray.get(0))
    }
}
