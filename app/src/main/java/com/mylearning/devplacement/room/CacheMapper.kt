package com.mylearning.devplacement.room

import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.utils.EntityMapper
import javax.inject.Inject

/**
 * This class is responsible for mapping the domain object to the network object
 */
class CacheMapper  @Inject constructor() : EntityMapper<UserCacheEntity, User> {
    override fun mapFromEntity(entity: UserCacheEntity): User {
        return User(
            id = entity.id,
            name = entity.name,
            image = entity.image,
            gender = entity.gender,
            colors = entity.colors,
            countries = entity.countries,
            date = entity.date

        )
    }

    override fun mapToEntity(domainModel: User): UserCacheEntity {
        return UserCacheEntity(
            id = domainModel.id,
            name = domainModel.name,
            image = domainModel.image,
            gender = domainModel.gender,
            countries = domainModel.countries,
            colors = domainModel.colors,
            date = domainModel.date
        )

    }

    // function responsible for converting list of entities object to User object
    fun mapFromEntityList (entities : List<UserCacheEntity>) : List<User> {
        return entities.map { mapFromEntity(it) }
    }

}