package gamis214.retrofit_realm_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.List;
import java.util.Random;

import gamis214.retrofit_realm_example.Models.Core.Content;
import gamis214.retrofit_realm_example.Models.Core.Person;
import gamis214.retrofit_realm_example.Models.Feed.Data;
import gamis214.retrofit_realm_example.Models.Feed.FeedResponse;
import gamis214.retrofit_realm_example.Models.Feed.States;
import gamis214.retrofit_realm_example.Service.ApiInterface;
import gamis214.retrofit_realm_example.Service.ApiClient;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = this.getClass().getSimpleName();
    private Button btnService,btnInsert;
    private TextView txtResult,txtStates;
    private ProgressBar progressBar;
    private LinearLayout content_result;
    private RealmConfiguration realmConfig;
    private Realm realm;
    private FeedResponse responseFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setItems();
        setStethoRealm();
        setRealm();
    }

    private void setStethoRealm() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }

    private void setRealm() {
        realmConfig = new RealmConfiguration.Builder(this)
                .name("Test.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnService:
                progressBar.setVisibility(View.VISIBLE);
                getTheService();
                break;
            case R.id.btnInsert:
                content_result.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                inserDataRealm();
                break;
        }
    }

    public void setItems(){
        content_result = (LinearLayout) findViewById(R.id.content_result);
        btnService = (Button) findViewById(R.id.btnService);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtStates = (TextView) findViewById(R.id.txtStates);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnService.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
    }

    public void getTheService(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<FeedResponse> call = apiService.getServiceExample(2);
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                Log.i(TAG,response.message());
                if(response.body() != null && response.isSuccessful()){
                    responseFeed = response.body();
                    setData(response.body().getData(),response.body().getStates());
                }else{
                    Log.e(TAG,response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    public void inserDataRealm(){
        //-->Parsing Model Feed to ModelCore
        final Content content = new Content();

        gamis214.retrofit_realm_example.Models.Core.Data data = new gamis214.retrofit_realm_example.Models.Core.Data();
        Person person = new Person();

        person.setName(responseFeed.getData().getPerson().getName());
        person.setAge(responseFeed.getData().getPerson().getAge());
        person.setUbication(responseFeed.getData().getPerson().getUbication());

        data.setPerson(person);

        RealmList<gamis214.retrofit_realm_example.Models.Core.States> lstStates = new RealmList<>();
        for (States states : responseFeed.getStates()) {
            gamis214.retrofit_realm_example.Models.Core.States state = new gamis214.retrofit_realm_example.Models.Core.States();
            state.setCity(states.getCity());
            lstStates.add(state);
        }

        int id = new Random().nextInt(500);
        Log.i(TAG,"Id --> " + id);

        content.setId(id);
        content.setData(data);
        content.setStates(lstStates);

        realm = Realm.getInstance(realmConfig);

        final RealmResults<Content> result = realm.where(Content.class).findAll();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(content);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                content_result.setVisibility(View.VISIBLE);
                txtResult.setText("SIZE OF BD");
                txtStates.setText("--> " + result.size());
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG,"Error in REALM: " + error.getMessage());
            }
        });

    }

    private void setData(Data data, List<States> states) {
        progressBar.setVisibility(View.GONE);
        content_result.setVisibility(View.VISIBLE);

        StringBuilder stringStates = new StringBuilder();

        txtResult.setText("Name: " + data.getPerson().getName() + "Age: " + data.getPerson().getAge() + "Ubication: " + data.getPerson().getUbication());
        for (States state : states) {
            stringStates.append("\n"+state.getCity());
        }
        txtStates.setText(stringStates.toString());
    }
}
