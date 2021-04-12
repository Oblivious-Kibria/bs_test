package com.bs.androidtest.networking

import com.bs.androidtest.data.PictureListResponse
import io.reactivex.Observable
import javax.inject.Inject


class PictureRepository @Inject constructor(private val apiService: ApiService) {

    fun requestPictureList(
        page: Int,
        limit: Int
    ): Observable<PictureListResponse> {
        return apiService.executePictureListRequest(
            page,
            limit
        )
    }

}