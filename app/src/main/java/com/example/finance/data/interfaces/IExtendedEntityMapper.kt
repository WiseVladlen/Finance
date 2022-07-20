package com.example.finance.data.interfaces

interface IExtendedEntityMapper<EntityOneFrom, EntityTwoFrom, EntityTo> {
    fun mapEntity(entityOne : EntityOneFrom, entityTwo : EntityTwoFrom) : EntityTo
}