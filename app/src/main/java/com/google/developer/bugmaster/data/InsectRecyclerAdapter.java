package com.google.developer.bugmaster.data;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.developer.bugmaster.R;

/**
 * RecyclerView adapter extended with project-specific required methods.
 */

public class InsectRecyclerAdapter extends
        RecyclerView.Adapter<InsectRecyclerAdapter.InsectHolder> {

    private static final String TAG = InsectRecyclerAdapter.class.getSimpleName();
    private Context mContext;
    private Cursor mCursor;


    public InsectRecyclerAdapter(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    /* ViewHolder for each insect item */
    public class InsectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvCommomName;
        TextView tvScientName;
        TextView tvDangerLevel;

        public InsectHolder(View itemView) {
            super(itemView);
            tvCommomName = (TextView) itemView.findViewById(R.id.tv_commom_name);
            tvScientName = (TextView)itemView.findViewById(R.id.tv_scient_name);
            tvDangerLevel = (TextView)itemView.findViewById(R.id.tv_danger_level);
        }

        @Override
        public void onClick(View v) {
        }
    }

    @Override
    public InsectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_insect, parent, false);
        InsectHolder holder = new InsectHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(InsectHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(InsectContract.InsectEntry.COLUMN_FRIENDLY_NAME));
        String scientificName = mCursor.getString(mCursor.getColumnIndex(InsectContract.InsectEntry.COLUMN_SCIENTIFIC_NAME));
        int dangerLevel = mCursor.getInt(mCursor.getColumnIndex(InsectContract.InsectEntry.COLUMN_DANGER_LEVEL));

        holder.tvCommomName.setText(name);
        holder.tvScientName.setText(scientificName);
        holder.tvDangerLevel.setText(String.valueOf(dangerLevel));

        GradientDrawable gd = (GradientDrawable) holder.tvDangerLevel.getBackground();
        String[] colors = mContext.getResources().getStringArray(R.array.dangerColors);
        int color = Color.parseColor(colors[dangerLevel -1]);
        gd.setColor(color);


    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    /**
     * Return the {@link Insect} represented by this item in the adapter.
     *
     * @param position Adapter item position.
     *
     * @return A new {@link Insect} filled with this position's attributes
     *
     * @throws IllegalArgumentException if position is out of the adapter's bounds.
     */
    public Insect getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        } else if (mCursor.moveToPosition(position)) {
            return new Insect(mCursor);
        }
        return null;
    }

    public void setIsectsData(Cursor c){
        mCursor = c;
        notifyDataSetChanged();
    }
}
