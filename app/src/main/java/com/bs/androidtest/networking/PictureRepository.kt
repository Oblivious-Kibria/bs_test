package com.bs.androidtest.networking

import com.bs.androidtest.data.PictureListResponse
import io.reactivex.Observable


object PictureRepository {

    fun requestPictureList(
        page: Int,
        limit: Int
    ): Observable<PictureListResponse> {
        return RetrofitClient.apiService.executePictureListRequest(
            page,
            limit
        )
    }

}