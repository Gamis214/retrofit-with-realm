package gamis214.retrofit_realm_example.Service;

import gamis214.retrofit_realm_example.Models.Feed.FeedResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pro on 22/09/16.
 */

public interface ApiInterface {

    @GET("bONmQaxZwy")
    Call<FeedResponse> getServiceExample(@Query("indent") int indent);

}
