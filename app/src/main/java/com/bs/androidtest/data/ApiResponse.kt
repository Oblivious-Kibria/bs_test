package com.bs.androidtest.data

/**
 * Created by User on 7/24/2019.
 */
class ApiResponse private constructor(
        val status: Status,
        val data: Any?,
        val error: Throwable?
) {

    companion object {
        fun loading(): ApiResponse {
            return ApiResponse(
                    Status.LOADING,
                    null,
                    null
            )
        }

        fun success(
                data: Any,
                message: String?
        ): ApiResponse {
            return ApiResponse(
                    Status.SUCCESS,
                    data,
                    null
            )
        }


        fun error(
                error: Throwable?
        ): ApiResponse {
            return ApiResponse(
                    Status.ERROR,
                    null,
                    error
            )
        }
    }

}