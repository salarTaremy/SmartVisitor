package Adapters.FragmentPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomerFragmentAdapter   extends FragmentPagerAdapter {
    List<Fragment> Fragments = new ArrayList<>();
    List<String> Titles = new ArrayList<>();

    public CustomerFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public CustomerFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return this.Fragments.get(position);
    }

    @Override
    public int getCount() {
        return  this.Fragments.size() ;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return  this.Titles.get(position);
    }

    public  void  AddToList(Fragment Fragment , String Title){
        this.Titles.add(Title);
        this.Fragments.add(Fragment);
    }
    public  void  ReplaceList(int position,Fragment Fragment , String Title){
        this.Titles.set(position,Title);
        this.Fragments.set(position,Fragment);
    }
}
