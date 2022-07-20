package com.example.finance.data.interfaces

interface IEntityMapper<EntityFrom, EntityTo> {
    fun mapEntity(entity : EntityFrom) : EntityTo
}