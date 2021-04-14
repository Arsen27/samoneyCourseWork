package com.example.samoney;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AddNewAdapter extends FragmentStateAdapter {

    public AddNewAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new AddOperationFragment();
            case 2:
                return new AddLimitFragment();
        }

        return new AddBillFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
