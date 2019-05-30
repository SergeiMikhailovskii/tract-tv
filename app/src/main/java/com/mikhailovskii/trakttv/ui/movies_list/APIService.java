package com.mikhailovskii.trakttv.ui.movies_list;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("movies/trending")
    Call<List<MoviesListResponse>> getMovies();

}
