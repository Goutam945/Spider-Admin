package com.impetrosys.spideradmin.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Fragment_Ad_Userslist extends FragmentStateAdapter {
    public Fragment_Ad_Userslist(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment=null;
        switch (position)
        {
            case 0 :
                fragment=new Fragment_Userslist();
                break;
            case 1:
                fragment=new Fragment_Referaluserlist();
                break;

        }

     return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
