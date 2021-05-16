package com.erbe.petsavesecurity.core.domain.model.animal

data class Media(
    val photos: List<Photo>,
    val videos: List<Video>
) {

    companion object {
        private const val EMPTY_MEDIA = ""
    }

    fun getFirstSmallestAvailablePhoto(): String {
        if (photos.isEmpty()) return EMPTY_MEDIA

        return photos.first().getSmallestAvailablePhoto()
    }

    data class Photo(
        val medium: String,
        val full: String
    ) {
        fun getSmallestAvailablePhoto(): String {
            return if (medium.isNotEmpty()) {
                medium
            } else {
                full
            }
        }
    }

    data class Video(val video: String)
}