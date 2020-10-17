package com.bs.androidtest.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bs.androidtest.data.ApiResponse
import com.bs.androidtest.data.PictureListResponse
import com.bs.androidtest.networking.PictureRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mutableLiveData: MutableLiveData<ApiResponse> = MutableLiveData<ApiResponse>()


    fun dispatchPictureListRequest() {
        compositeDisposable.add(
                PictureRepository.requestPictureList(
                        1, 100
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { mutableLiveData.setValue(ApiResponse.loading()) }
                        .subscribe(
                                { response ->
                                    Log.d("ApiTesting", "dispatchPictureListRequest onSuccess ${response[0].downloadUrl}")
                                    mutableLiveData.value = ApiResponse.success(response, null)
                                },
                                { throwable ->
                                    Log.d("ApiTesting", "onError $throwable")
                                    mutableLiveData.value = ApiResponse.error(throwable)
                                }
                        ))
    }


    fun getApiResponse(): MutableLiveData<ApiResponse> {
        return mutableLiveData
    }

}