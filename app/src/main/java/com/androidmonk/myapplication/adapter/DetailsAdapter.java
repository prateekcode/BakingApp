package com.androidmonk.myapplication.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.androidmonk.myapplication.R;
import com.androidmonk.myapplication.fragment.IngredientsFragment;
import com.androidmonk.myapplication.fragment.StepsFragment;
import com.androidmonk.myapplication.utils.Constant;

import static com.androidmonk.myapplication.utils.Constant.INGREDIENTS;
import static com.androidmonk.myapplication.utils.Constant.STEPS;

public class DetailsAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private int mNumIngredients;
    private int mNumSteps;


    public DetailsAdapter(Context context, FragmentManager fm, int numIngredients, int numSteps) {
        super(fm);
        mContext = context;
        mNumIngredients = numIngredients;
        mNumSteps = numSteps;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case INGREDIENTS:
                return new IngredientsFragment();
            case STEPS:
                return new StepsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return Constant.PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case INGREDIENTS:
                String title = Constant.TAP_TITLE[position % Constant.PAGE_COUNT].toUpperCase();
                title += mContext.getString(R.string.space) +
                        mContext.getString(R.string.open_parenthesis) + mNumIngredients +
                        mContext.getString(R.string.close_parenthesis);
                return title;
            case STEPS:
                title = Constant.TAP_TITLE[position % Constant.PAGE_COUNT].toUpperCase();
                title += mContext.getString(R.string.space) +
                        mContext.getString(R.string.open_parenthesis) + mNumSteps +
                        mContext.getString(R.string.close_parenthesis);
                return title;
        }
        return null;

    }
}
