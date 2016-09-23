package gamis214.retrofit_realm_example.Models.Feed;

import java.util.List;

/**
 * Created by pro on 22/09/16.
 */

public class FeedResponse {

    private List<States> States;
    private Data Data;

    public Data getData() {
        return Data;
    }

    public void setData(Data data) {
        Data = data;
    }

    public List<gamis214.retrofit_realm_example.Models.Feed.States> getStates() {
        return States;
    }

    public void setStates(List<gamis214.retrofit_realm_example.Models.Feed.States> states) {
        States = states;
    }
}
