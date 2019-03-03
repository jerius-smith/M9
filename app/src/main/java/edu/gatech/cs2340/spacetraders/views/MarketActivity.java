package edu.gatech.cs2340.spacetraders.views;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import edu.gatech.cs2340.spacetraders.R;
import edu.gatech.cs2340.spacetraders.model.Inventory;
import edu.gatech.cs2340.spacetraders.viewmodel.ConfigurationViewModel;
import edu.gatech.cs2340.spacetraders.viewmodel.MarketViewModel;

public class MarketActivity extends AppCompatActivity {

    private RecyclerView marketInventoryView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    MarketViewModel viewModel;

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
    }

}
