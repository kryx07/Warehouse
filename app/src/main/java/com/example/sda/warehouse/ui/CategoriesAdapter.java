package com.example.sda.warehouse.ui;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.model.beans.Category;

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

        void onEditClick(Category category);

        void onItemCheck(Category category);

        void onItemUnCheck(Category category);


    }


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

        final Category currentCategory = categoryList.get(position);

        holder.checkBox.setChecked(false);

        ((CategoryHolder) holder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               holder.checkBox.setChecked((holder.checkBox.isChecked())); ///?????
                if (holder.checkBox.isChecked()) {
                    categoryClickListener.onItemCheck(currentCategory);
                }else {
                    categoryClickListener.onItemUnCheck(currentCategory);
                }
            }
        });

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
        @BindView(R.id.edit_icon)
        ImageView editButton;
        @BindView(R.id.item_checkbox)
        CheckBox checkBox;

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
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryClickListener.onEditClick(category);
                }
            });


          /*  checkBox.setChecked(false);*/

           /* checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryClickListener.onCheckBoxClick(category);
                }
            });*/


        }


        public void setCategory(Category category) {
            this.category = category;
            categoryName.setText(category.getName());
            if (category.getParentCategory() == null) {
                categoryParent.setText("Parent: " + "Empty");
            } else {
                categoryParent.setText("Parent: " + category.getParentCategory());
            }
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            checkBox.setOnClickListener(onClickListener);
        }
    }

}


