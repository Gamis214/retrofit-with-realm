package gamis214.retrofit_realm_example.Models.Core;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by pro on 22/09/16.
 */

public class Content extends RealmObject {
    @PrimaryKey
    private int id;
    private RealmList<States> States;
    private Data Data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<States> getStates() {
        return States;
    }

    public void setStates(RealmList<gamis214.retrofit_realm_example.Models.Core.States> states) {
        States = states;
    }

    public gamis214.retrofit_realm_example.Models.Core.Data getData() {
        return Data;
    }

    public void setData(gamis214.retrofit_realm_example.Models.Core.Data data) {
        Data = data;
    }
}
