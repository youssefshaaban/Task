package com.example.taskjobs.data

import com.example.taskjobs.App
import com.example.taskjobs.BASE_URL
import com.example.taskjobs.utils.LogUtil


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
const val NO_INTERNET_CONNECTION = -1
private const val timeoutRead = 30   //In seconds
private const val contentType = "Content-Type"
private const val accept_language = "Accept-Language"
private const val authrization = "Authorization"
private const val contentTypeValue = "application/json"
private const val timeoutConnect = 30   //In seconds
private const val academyId = "AcademyId"
private const val academyIdValue = "1"

class ServiceGenerator() {
    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit

    private var headerInterceptor = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header(contentType, contentTypeValue)

            .method(original.method, original.body)

        requestBuilder.addHeader(academyId, academyIdValue)
        val request = requestBuilder.build()
        try {
            val url = requestBuilder.build().url.toString()
            LogUtil.error("urlHelper", url)
            LogUtil.error(
                "bodyHelper",
                bodyToString(request.body) + ""
            )
            LogUtil.error(
                "headerHelper",
                request.headers.toString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        chain.proceed(request)
    }


    private fun bodyToString(body: RequestBody?): String {
        try {
            val buffer = Buffer()
            if (body != null)
                body.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }
    }

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
//            if (BuildConfig.DEBUG) {
//                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
//            }
            loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            return loggingInterceptor
        }

    init {
        okHttpBuilder.addInterceptor(headerInterceptor)
        okHttpBuilder.addInterceptor(logger)

        okHttpBuilder.connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)
        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

}
