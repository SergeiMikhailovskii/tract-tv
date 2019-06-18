package com.mikhailovskii.trakttv.data.api

import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.data.entity.MovieTrack

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPI {

    @get:GET("/movies/trending")
    val movies: Observable<List<MovieTrack>>

    @GET("/movies/{slug}?extended=full")
    fun getExtendedInfo(@Path("slug") slug: String): Observable<Movie>

}