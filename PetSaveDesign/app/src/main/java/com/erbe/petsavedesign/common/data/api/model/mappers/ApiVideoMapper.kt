package com.erbe.petsavedesign.common.data.api.model.mappers

import com.erbe.petsavedesign.common.data.api.model.ApiVideoLink
import com.erbe.petsavedesign.common.domain.model.animal.Media
import javax.inject.Inject

class ApiVideoMapper @Inject constructor() : ApiMapper<ApiVideoLink?, Media.Video> {

    override fun mapToDomain(apiEntity: ApiVideoLink?): Media.Video {
        return Media.Video(video = apiEntity?.embed.orEmpty())
    }
}