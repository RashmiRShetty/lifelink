package com.example.lifelink;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RegisterPagerAdapter extends FragmentStateAdapter {

    public RegisterPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? new StepOneFragment() : new StepTwoFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
