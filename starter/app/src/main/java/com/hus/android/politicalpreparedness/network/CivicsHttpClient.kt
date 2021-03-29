package com.hus.android.politicalpreparedness.network

import okhttp3.OkHttpClient

class CivicsHttpClient: OkHttpClient() {

    companion object {

        //const val API_KEY = "AIzaSyDFWraTR7_Zopb02Ex6A4LRkMpMRUbuLlw"
        const val API_KEY = "AIzaSyC9uuqmbSxbAoErWJ2Hd9339No4aLdnN2s"
        //TODO: Place your API Key Here

        fun getClient(): OkHttpClient {
            return Builder()
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val url = original
                                .url()
                                .newBuilder()
                                .addQueryParameter("key", API_KEY)
                                .build()
                        val request = original
                                .newBuilder()
                                .url(url)
                                .build()
                        chain.proceed(request)
                    }
                    .build()
        }

    }

}