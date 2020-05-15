package com.androidmonk.myapplication;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import com.androidmonk.myapplication.adapter.RecipeAdapter;
import com.androidmonk.myapplication.api.BakingApi;
import com.androidmonk.myapplication.api.BakingHelper;
import com.androidmonk.myapplication.model.Recipe;
import com.androidmonk.myapplication.utils.Constant;
import com.androidmonk.myapplication.utils.GridAutofitLayoutManager;
import com.androidmonk.myapplication.utils.SimpleIdlingResource;
import com.androidmonk.myapplication.widget.RecipeWidgetProvider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androidmonk.myapplication.utils.Constant.GRID_COLUMN_WIDTH;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener {

    private RecipeAdapter mRecipeAdapter;
    private BakingApi mBakingService;

    GridAutofitLayoutManager layoutManager;

    @BindView(R.id.itemList)
    RecyclerView recipesListRecyclerView;

    @Nullable
    private SimpleIdlingResource mIdlingResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        mBakingService = BakingHelper.getInstance(MainActivity.this);
        layoutManager = new GridAutofitLayoutManager(
                this, GRID_COLUMN_WIDTH);

        getIdlingResource();
        getAllRecipes();
    }

    private void getAllRecipes() {
        if (mIdlingResource!=null)
            mIdlingResource.setIdleState(false);

        Call<List<Recipe>> call = mBakingService.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {

                if(response.isSuccessful()){
                    mRecipeAdapter = new RecipeAdapter(response.body(),getApplicationContext(),MainActivity.this);
                    recipesListRecyclerView.setLayoutManager(layoutManager);
                    recipesListRecyclerView.setAdapter(mRecipeAdapter);
                }

                mIdlingResource.setIdleState(true);


            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, Throwable t) {
                t.printStackTrace();
                mIdlingResource.setIdleState(true);

            }
        });

    }

    @Override
    public void onRecipeItemClicked(Recipe recipe) {
        Bundle b = new Bundle();
        b.putParcelable(Constant.RECIPE, recipe);

        updateSharedPreference(recipe);
        sendBroadcastToWidget();

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Constant.RECIPE,b);
        startActivity(intent);
    }

    private void updateSharedPreference(Recipe recipe) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String ingredientString = Constant.toIngredientString(recipe.getmIngredients());

        editor.putString(getString(R.string.pref_ingredient_list_key), ingredientString);
        editor.putString(getString(R.string.pref_recipe_name_key), recipe.getmName());

        String stepString = Constant.toStepString(recipe.getmSteps());

        editor.putInt(getString(R.string.pref_recipe_id_key), recipe.getmId());
        editor.putString(getString(R.string.pref_step_list_key), stepString);
        editor.putString(getString(R.string.pref_image_key), recipe.getmImage());
        editor.putInt(getString(R.string.pref_servings_key), recipe.getmServings());

        editor.apply();
    }

    private void sendBroadcastToWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        Intent updateAppWidgetIntent = new Intent();
        updateAppWidgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateAppWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        sendBroadcast(updateAppWidgetIntent);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

}
