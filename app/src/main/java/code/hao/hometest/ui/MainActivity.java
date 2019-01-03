package code.hao.hometest.ui;

import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import code.hao.hometest.R;
import code.hao.hometest.data.ApiClient;
import code.hao.hometest.data.ApiService;
import code.hao.hometest.data.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiService apiService = ApiClient.getInstance();
        apiService.get().enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                ArrayList<Item> items = new ArrayList<>();
                ArrayList<String> body = response.body();
                for (int i = 0; i < body.size(); i++) {
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    Item item = new Item (body.get(i), color);
                    items.add(item);
                }

                RecyclerView recyclerView = findViewById(R.id.recyclerView);

                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(horizontalLayoutManagaer);

                RecyclerViewAdapter adapter = new RecyclerViewAdapter(MainActivity.this, items);
                adapter.setClickListener(MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Please check your network and reopen the app, please!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        // Nothing to do for now.
    }
}
