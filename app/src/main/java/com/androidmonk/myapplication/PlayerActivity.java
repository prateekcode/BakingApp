package com.androidmonk.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.androidmonk.myapplication.fragment.StepDetailFragment;
import com.androidmonk.myapplication.model.Recipe;
import com.androidmonk.myapplication.model.Step;
import com.androidmonk.myapplication.utils.Constant;

public class PlayerActivity extends AppCompatActivity {

    private Step mStep;
    private Recipe mRecipe;
    private int mStepIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        getRecipeAndStepIndex();
        setTitle(mRecipe.getmName());
        showUpButton();

        if (savedInstanceState == null) {

            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            mStep = mRecipe.getmSteps().get(mStepIndex);
            stepDetailFragment.setStep(mStep);
            stepDetailFragment.setStepIndex(mStepIndex);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        }
    }

    private void getRecipeAndStepIndex() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Constant.EXTRA_STEP_INDEX)) {
                // Get the correct step index from the intent
                Bundle b = intent.getBundleExtra(Constant.EXTRA_STEP_INDEX);
                mStepIndex = b.getInt(Constant.EXTRA_STEP_INDEX);
            }
            if (intent.hasExtra(Constant.RECIPE)) {
                // Get the recipe from the intent
                Bundle b = intent.getBundleExtra(Constant.RECIPE);
                mRecipe = b.getParcelable(Constant.RECIPE);
            }
        }
    }

    private void showUpButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
