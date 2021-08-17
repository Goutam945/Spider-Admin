package com.impetrosys.spideradmin.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment=null;
        switch (position)
        {
            case 0 :
                fragment=new Fragment_depositlist();
                break;
            case 1:
                fragment=new Fragment_depositlist_id();
                break;

        }

     return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
