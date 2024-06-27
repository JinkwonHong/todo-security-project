package com.teamsparta.todosecurityproject.common.time.model

import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.ZoneId
import java.time.ZonedDateTime

@MappedSuperclass
abstract class BaseTime {

    var createdAt: ZonedDateTime? = null
    var updatedAt: ZonedDateTime? = null

    @PrePersist
    fun setCreatedAt() {
        val now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        this.createdAt = now
        this.updatedAt = now
    }

    @PreUpdate
    fun setUpdatedAt() {
        this.updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    }
}