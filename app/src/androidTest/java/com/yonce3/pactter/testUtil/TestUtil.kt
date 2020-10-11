package com.yonce3.pactter.testUtil

import com.yonce3.pactter.data.entity.User

class TestUtil {
    companion object {
        fun createUser(id: Int, name: String): User {
            return User(userId = id, name = name)
        }
    }
}