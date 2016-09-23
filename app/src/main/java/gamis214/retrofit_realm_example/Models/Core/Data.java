package gamis214.retrofit_realm_example.Models.Core;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by pro on 22/09/16.
 */

public class Data extends RealmObject {

    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
