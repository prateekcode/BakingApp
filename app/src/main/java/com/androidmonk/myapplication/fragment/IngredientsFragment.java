package com.androidmonk.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidmonk.myapplication.R;
import com.androidmonk.myapplication.adapter.IngredientsAdapter;
import com.androidmonk.myapplication.model.Ingredient;
import com.androidmonk.myapplication.model.Recipe;
import com.androidmonk.myapplication.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsFragment extends Fragment implements IngredientsAdapter.IngredientsAdapterOnClickHandler {

    private Recipe mRecipe;

    private IngredientsAdapter mIngredientsAdapter;

    List<Ingredient> ingredients = new ArrayList<>();

    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredient_details, container, false);
        ButterKnife.bind(this,view);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(Constant.RECIPE)) {
                Bundle b = intent.getBundleExtra(Constant.RECIPE);
                mRecipe = b.getParcelable(Constant.RECIPE);

                ingredients = mRecipe.getmIngredients();

                mIngredientsAdapter = new IngredientsAdapter(ingredients, this,
                        mRecipe.getmName());

                LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
                rvIngredients.setLayoutManager(layoutManager);
                rvIngredients.setHasFixedSize(true);
                rvIngredients.setAdapter(mIngredientsAdapter);
            }
        }

        return view;
    }

    @Override
    public void onIngredientClick(int ingredientIndex) {

    }
}

