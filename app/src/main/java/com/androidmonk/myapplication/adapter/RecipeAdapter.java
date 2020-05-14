package com.androidmonk.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidmonk.myapplication.R;
import com.androidmonk.myapplication.model.Recipe;
import com.androidmonk.myapplication.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewholder> {

    private List<Recipe> recipes;
    private Context context;
    private final RecipeClickListener listener;

    public RecipeAdapter(List<Recipe> recipes, Context context, RecipeClickListener listener) {
        this.recipes = recipes;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new RecipeViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewholder holder, int position) {
        holder.bind(recipes.get(position),listener,position);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipeImage)
        ImageView image;
        @BindView(R.id.recipeName)
        TextView name;

        public RecipeViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(final Recipe recipe, final RecipeClickListener recipeClickListener, int position)
        {
            name.setText(recipe.getmName());

            int imageResourceId = Constant.getImageResource(position);
            image.setImageResource(imageResourceId);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recipeClickListener.onRecipeItemClicked(recipe);
                }
            });

        }
    }
    public interface RecipeClickListener{
        void onRecipeItemClicked(Recipe recipe);
    }
}
