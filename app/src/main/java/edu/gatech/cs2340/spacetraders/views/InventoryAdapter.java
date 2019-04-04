package edu.gatech.cs2340.spacetraders.views;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import edu.gatech.cs2340.spacetraders.model.Good;
import edu.gatech.cs2340.spacetraders.model.Inventory;
import edu.gatech.cs2340.spacetraders.R;

/**
 * The type Inventory adapter.
 */
public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> {
    private final Inventory inventory;
    private final Inventory playerInventory;
    private int selectedPosition = -1;

    /**
     * The type My view holder.
     */
// Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Table row.
         */
// each data item is just a string in this case
        final TableRow tableRow;

        /**
         * Instantiates a new My view holder.
         *
         * @param v the v
         */
        MyViewHolder(TableRow v) {
            super(v);
            tableRow= v;
        }
    }

    /**
     * Instantiates a new Inventory adapter.
     *
     * @param inventory       the inventory
     * @param playerInventory the player inventory
     */
// Provide a suitable constructor (depends on the kind of dataset)
    public InventoryAdapter(Inventory inventory, Inventory playerInventory) {
        this.inventory = inventory;
        this.playerInventory = playerInventory;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public InventoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TableRow v = (TableRow) inflater
                .inflate(R.layout.inventory_item, parent, false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Good good = Good.values()[position];

        TextView goodName = (TextView) holder.tableRow.getChildAt(0);
        TextView goodPrice = (TextView) holder.tableRow.getChildAt(1);
        TextView marketStock = (TextView) holder.tableRow.getChildAt(2);
        TextView playerStock = (TextView) holder.tableRow.getChildAt(3);

        goodName.setText(good.toString());
        goodPrice.setText(String.format("$%.2f", inventory.getPrice(good)));
        marketStock.setText(String.format("%d", inventory.getStock(good)));
        playerStock.setText(String.format("%d", playerInventory.getStock(good)));

        holder.tableRow.setOnClickListener(view -> {
            selectedPosition = good.ordinal();
            holder.tableRow.setBackgroundColor(Color.parseColor("#baefcd"));
            notifyDataSetChanged();
            updateColor(holder, position);

        });

        updateColor(holder, position);


    }

    /**
     * Gets selected position.
     *
     * @return the selected position
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return Good.values().length;
    }

    private void updateColor(MyViewHolder holder, int position) {
        if (position == selectedPosition) {
            holder.tableRow.setBackgroundColor(Color.parseColor("#baefcd"));
        } else {
            holder.tableRow.setBackgroundColor(Color.parseColor("#95d4ab"));
        }
    }

}
