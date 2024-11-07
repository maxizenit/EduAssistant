package ru.itmo.eduassistant.bot.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Chat(@Id val id: Long, @Column(nullable = false, unique = true) val userId: Long)
