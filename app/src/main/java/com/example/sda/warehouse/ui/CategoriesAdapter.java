package com.example.sda.warehouse.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.model.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryHolder> {


    public interface CategoryClickListener {
        /**
         * Called when an item is clicked.
         *
         * @param category to be passed .
         */
        void onCategoryClick(Category category);
        void onDeleteClick(Category category);
    }

    // TODO: 12.06.17
    /*
    List of checked boxed to be implemented
    https://stackoverflow.com/questions/33434626/get-list-of-checked-checkboxes-from-recyclerview-android
     */

    public CategoriesAdapter(CategoryClickListener categoryClickListener) {
        this.categoryClickListener = categoryClickListener;
    }

    private CategoryClickListener categoryClickListener;

    private List<Category> categoryList = new ArrayList<>();

    public void setData(List<Category> categoryList) {
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);
        notifyDataSetChanged();

    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_category, parent, false);

        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryHolder holder, final int position) {
        holder.setCategory(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_name)
        TextView categoryName;
        @BindView(R.id.category_parent)
        TextView categoryParent;
        @BindView(R.id.delete_icon)
        ImageView deleteButton;
        private Category category;

        public CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryClickListener.onCategoryClick(category);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryClickListener.onDeleteClick(category);
                }
            });

        }


        public void setCategory(Category category) {
            this.category = category;
            categoryName.setText(category.getName());
            categoryParent.setText("Parent: " + category.getParentCategory());
        }

    }


}