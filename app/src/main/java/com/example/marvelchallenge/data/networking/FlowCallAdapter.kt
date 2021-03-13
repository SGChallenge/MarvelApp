/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marvelchallenge.data.networking

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * A Retrofit adapter that converts the Call into a LiveData of com.example.myapplication.data.networking.ApiResponse.
 * @param <R>
</R> */
@OptIn(ExperimentalCoroutinesApi::class)
internal class FlowCallAdapter<R>(
    private val responseType: Type
) : CallAdapter<R, Flow<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Flow<ApiResponse<R>> {
        return callbackFlow {
            call.enqueue(
                object : Callback<R> {
                    override fun onResponse(call: Call<R>, response: Response<R>) {
                        if (!this@callbackFlow.isClosedForSend) {
                            Log.d("GG", call.request().url.toUrl().toString())
                            val apiResponse = ApiResponse.create(response)
                            offer(apiResponse)
                        }
                    }

                    override fun onFailure(call: Call<R>, throwable: Throwable) {
                        Log.e("GG", call.request().url.toUrl().toString())
                        offer(ApiResponse.create<R>(throwable))
                    }
                }
            )

            awaitClose {
                call.cancel()
            }
        }
    }
}
