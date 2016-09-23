package gamis214.retrofit_realm_example.Models.Core;

import java.util.List;
import gamis214.retrofit_realm_example.Models.Core.States;
import io.realm.RealmObject;

/**
 * Created by pro on 22/09/16.
 */

public class Person extends RealmObject {

    private String Ubication;
    private String Name;
    private int Age;

    public String getUbication() {
        return Ubication;
    }

    public void setUbication(String ubication) {
        Ubication = ubication;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

}
