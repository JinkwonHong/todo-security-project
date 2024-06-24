package com.teamsparta.todosecurityproject.common.model

import java.time.ZoneId
import java.time.ZonedDateTime

abstract class BasedTime {

    var createdAt: ZonedDateTime? = null
    var updatedAt: ZonedDateTime? = null

    fun setCreatedAt() {
        val now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        this.createdAt = now
        this.updatedAt = now
    }

    fun setUpdatedAt() {
        this.updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    }
}
