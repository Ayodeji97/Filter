package com.mylearning.devplacement.utils

/* Interface responsible for mapping mapping objects */
interface EntityMapper <Entity, DomainModel> {

    fun mapFromEntity (entity: Entity) : DomainModel

    fun mapToEntity (domainModel: DomainModel) : Entity
}