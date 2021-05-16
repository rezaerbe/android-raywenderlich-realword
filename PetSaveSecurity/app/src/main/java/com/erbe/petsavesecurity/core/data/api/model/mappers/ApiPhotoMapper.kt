package com.erbe.petsavesecurity.core.data.api.model.mappers

import com.erbe.petsavesecurity.core.data.api.model.ApiPhotoSizes
import com.erbe.petsavesecurity.core.domain.model.animal.Media
import javax.inject.Inject

class ApiPhotoMapper @Inject constructor() : ApiMapper<ApiPhotoSizes?, Media.Photo> {

    override fun mapToDomain(apiEntity: ApiPhotoSizes?): Media.Photo {
        return Media.Photo(
            medium = apiEntity?.medium.orEmpty(),
            full = apiEntity?.full.orEmpty()
        )
    }
}