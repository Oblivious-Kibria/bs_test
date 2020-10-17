package com.bs.androidtest.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bs.androidtest.data.PictureListResponse
import com.bs.androidtest.networking.PictureRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mutableLiveData: MutableLiveData<PictureListResponse> = MutableLiveData<PictureListResponse>()


    fun dispatchPictureListRequest() {
        compositeDisposable.add(
                PictureRepository.requestPictureList(
                        1, 100
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { }
                        .subscribe(
                                { response ->
                                    Log.d("ApiTesting", "dispatchPictureListRequest onSuccess ${response[0].downloadUrl}")
                                    mutableLiveData.value = response
                                },
                                { throwable ->
                                    Log.d("ApiTesting", "onError $throwable")

                                }
                        ))
    }


    fun getApiResponse(): MutableLiveData<PictureListResponse> {
        return mutableLiveData
    }

}