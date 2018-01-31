package com.soushin.cgank.network;

import com.soushin.cgank.entity.CategoryResult;
import com.soushin.cgank.entity.SearchResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/1/3.
 */

public interface CGankApi {

    @GET("data/{category}/{number}/{page}")
    Observable<CategoryResult> getCategoryDate(@Path("category") String category, @Path("number") int number, @Path("page") int page);

    @GET("random/data/福利/{number}")
    Observable<CategoryResult> getRandomBeauties(@Path("number") int number);

    @GET("search/query/{key}/category/all/count/{count}/page/{page}")
    Observable<SearchResult> getSearchResult(@Path("key") String key, @Path("count") int count, @Path("page") int page);

}
