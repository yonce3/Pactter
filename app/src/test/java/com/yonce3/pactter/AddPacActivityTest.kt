package com.yonce3.pactter

import com.yonce3.pactter.ui.AddPacActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AddPacActivityTest {
    lateinit var activity: AddPacActivity

    @Before
    fun setUp() {
        activity = AddPacActivity()
    }

    @Test
    fun testSavePacValidation() {
        assert(activity.savePack("")).equals(false)
        //activity.savePack("")
    }
}