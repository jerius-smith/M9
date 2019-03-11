package edu.gatech.cs2340.spacetraders.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.gatech.cs2340.spacetraders.R;
import edu.gatech.cs2340.spacetraders.model.Good;
import edu.gatech.cs2340.spacetraders.model.Inventory;
import edu.gatech.cs2340.spacetraders.viewmodel.ConfigurationViewModel;
import edu.gatech.cs2340.spacetraders.viewmodel.MarketViewModel;

public class MarketActivity extends AppCompatActivity {

    private RecyclerView marketInventoryView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    MarketViewModel viewModel;

    private ImageButton sellButton;
    private ImageButton buyButton;
    private TextView playerCredits;
    private TextView playerPlanet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        marketInventoryView = findViewById(R.id.market_inventory_view);
        marketInventoryView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        marketInventoryView.setLayoutManager(layoutManager);

        viewModel = ViewModelProviders.of(this).get(MarketViewModel.class);

        mAdapter = new InventoryAdapter(viewModel.getMarketInventory(),
                viewModel.getPlayerInventory());
        marketInventoryView.setAdapter(mAdapter);

        playerCredits = findViewById(R.id.credits_text);
        playerPlanet = findViewById(R.id.location_text);
        updatePlayerInfo();

        sellButton = findViewById(R.id.sell_button);
        buyButton = findViewById(R.id.buy_button);

        sellButton.setOnClickListener(view -> {
            InventoryAdapter adapter = (InventoryAdapter) marketInventoryView.getAdapter();
            if (adapter.getSelectedPosition() >=0) {
                int pos = adapter.getSelectedPosition();
                viewModel.sellItem(Good.values()[pos]);
                adapter.notifyDataSetChanged();
                updatePlayerInfo();
                viewModel.savePlayer();
            }
        });

        buyButton.setOnClickListener(view -> {
            InventoryAdapter adapter = (InventoryAdapter) marketInventoryView.getAdapter();
            if (adapter.getSelectedPosition() >=0) {
                int pos = adapter.getSelectedPosition();
                viewModel.buyItem(Good.values()[pos]);
                adapter.notifyDataSetChanged();
                updatePlayerInfo();
                viewModel.savePlayer();
            }
        });
        Log.d("TRAVEL", "On create: " + playerPlanet.getText().toString());
    }

    private void updatePlayerInfo() {
        playerCredits.setText(String.format("Credits: %.2f", viewModel.getPlayerCredits()));
        playerPlanet.setText(String.format("Location: %s", viewModel.getPlayerLocation()));
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePlayerInfo();
        Log.d("TRAVEL", "On resume: " + playerPlanet.getText().toString());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PlanetActivity.class));
    }

}
