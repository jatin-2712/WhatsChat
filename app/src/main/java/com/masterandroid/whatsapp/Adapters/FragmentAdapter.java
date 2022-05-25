package com.masterandroid.whatsapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.masterandroid.whatsapp.Fragments.CallsFragment;
import com.masterandroid.whatsapp.Fragments.ChatsFragment;
import com.masterandroid.whatsapp.Fragments.StatusFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    private String [] titles = new String[]{"CHATS","STATUS","CALLS"};

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch(position)
        {
            case 0 : return new ChatsFragment();
            case 1 : return new StatusFragment();
            case 2 : return new CallsFragment();
            default : return new ChatsFragment();
        }

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

}
