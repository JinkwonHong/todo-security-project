package com.teamsparta.todosecurityproject.domain.todo.model

import java.io.InvalidObjectException

enum class Category {
    WORK, STUDY, EXERCISE, PROMISE, OTHER;

    companion object {
        fun fromString(category: String) : Category {
            return try {
                valueOf(category.uppercase())
            } catch (e: IllegalArgumentException) {
                throw InvalidObjectException("unknown category: $category.")
            }
        }
    }

}