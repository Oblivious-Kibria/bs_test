package com.bs.androidtest.networking


import com.bs.androidtest.data.PictureListResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    @GET(ApiConstants.PIC_SUM_API)
    fun executePictureListRequest(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Observable<PictureListResponse>

}