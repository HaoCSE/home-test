package code.hao.hometest.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import code.hao.hometest.R;
import code.hao.hometest.data.Item;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private List<Item> items;

    public RecyclerViewAdapter(Context context, ArrayList<Item> items) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.items = items;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int color = items.get(position).getColor();
        String name = items.get(position).getName();
        holder.cardView.setCardBackgroundColor(color);
        StringTokenizer st = new StringTokenizer(name);
        TextView tv = new TextView(mContext);

        tv.setText(name);
        tv.setTextColor(mContext.getResources().getColor(android.R.color.white));
        tv.setGravity(Gravity.CENTER);

        float scale =  mContext.getResources().getDisplayMetrics().density;
        tv.measure(0, 0);
        int width = tv.getMeasuredWidth();

        if (st.countTokens() > 1) {
            tv.setWidth(width/2 + (int) (20*scale + 0.5f));
        } else {
            tv.setWidth(width + (int) (30*scale + 0.5f));
        }

        tv.setMaxLines(2);

        holder.cardView.removeAllViews();
        holder.cardView.addView(tv);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return items.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Item getItem(int id) {
        return items.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}