package com.cse344group10.disastermanagement.models.token

import com.cse344group10.disastermanagement.models.user.User

class SessionToken {
    companion object{
        var currentlyLoggedUser: User? = null
            private set

        fun initToken(user: User) {
            currentlyLoggedUser = user
        }
    }
}