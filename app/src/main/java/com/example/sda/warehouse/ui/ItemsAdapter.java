package com.example.sda.warehouse.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.model.beans.Bean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsAdapter<T extends Bean> extends RecyclerView.Adapter<ItemsAdapter.ItemHolder> {


    public interface ItemClickListener<T> {

        void onItemClick(final T item);

        void onDeleteClick(final T item);

        void onEditClick(final T item);

        void onItemCheck(final T item);

        void onItemUnCheck(final T item);

    }


    public ItemsAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private ItemClickListener itemClickListener;

    private List<T> itemsList = new ArrayList<>();

    public void setData(List<T> itemsList) {
        this.itemsList.clear();
        this.itemsList.addAll(itemsList);
        notifyDataSetChanged();

    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_item, parent, false);

        return new ItemHolder(view);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {
        holder.setItem(itemsList.get(position));

        final T currentItem = itemsList.get(position);

        holder.checkBox.setChecked(false);

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox.setChecked((holder.checkBox.isChecked())); ///?????
                if (holder.checkBox.isChecked()) {
                    itemClickListener.onItemCheck(currentItem);
                } else {
                    itemClickListener.onItemUnCheck(currentItem);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class ItemHolder<T extends Bean> extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_details)
        TextView itemDetails;

        @BindView(R.id.delete_icon)
        ImageView deleteButton;
        @BindView(R.id.edit_icon)
        ImageView editButton;
        @BindView(R.id.item_checkbox)
        CheckBox checkBox;

        private T item;

        @SuppressWarnings("unchecked")
        ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(item);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onDeleteClick(item);
                }
            });
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onEditClick(item);
                }
            });
        }

        void setItem(T item) {
            this.item = item;
            itemTitle.setText(item.getName());
            itemDetails.setText(item.getDetails());
        }

        void setOnClickListener(View.OnClickListener onClickListener) {
            checkBox.setOnClickListener(onClickListener);
        }
    }

}


