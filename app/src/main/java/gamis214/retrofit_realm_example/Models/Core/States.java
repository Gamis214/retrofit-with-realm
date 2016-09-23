package gamis214.retrofit_realm_example.Models.Core;

import io.realm.RealmObject;

/**
 * Created by pro on 22/09/16.
 */

public class States extends RealmObject {

    private String City;


    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
