package com.neopi.qq5.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.neopi.qq5.v.TextFragment;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author    :  NeoPi
 * Date      :  2017/11/27
 * Describe  :
 */

public class MainPageAdapter extends FragmentPagerAdapter {


    private FragmentManager fm ;
    private Context context ;
    private Map<String ,WeakReference<Fragment>> fragmentMap ;

    private String[] title = {"推荐","关注","发现","我的"};

    public MainPageAdapter(FragmentManager fm, Context context) {
        super(fm) ;
        this.fm = fm ;
        this.context = context ;
        fragmentMap = new LinkedHashMap<>(title.length) ;

        initFragment();
    }

    private void initFragment() {
        Bundle bundle = new Bundle() ;
        bundle.putString("title","First");
        TextFragment textFragment = new TextFragment() ;
        textFragment.setArguments(bundle);
        fragmentMap.put("0",new WeakReference<Fragment>(textFragment));


        Bundle bundle1 = new Bundle() ;
        bundle1.putString("title","Second");
        TextFragment textFragment1 = new TextFragment() ;
        textFragment1.setArguments(bundle1);
        fragmentMap.put("1",new WeakReference<Fragment>(textFragment1));


        Bundle bundle2 = new Bundle() ;
        bundle2.putString("title","Third");
        TextFragment textFragment2 = new TextFragment() ;
        textFragment2.setArguments(bundle2);
        fragmentMap.put("2",new WeakReference<Fragment>(textFragment2));


        Bundle bundle3 = new Bundle() ;
        bundle3.putString("title","Fourth ");
        TextFragment textFragment3 = new TextFragment() ;
        textFragment3.setArguments(bundle3);
        fragmentMap.put("3",new WeakReference<Fragment>(textFragment3));
    }

    @Override
    public Fragment getItem(int position) {
        WeakReference<Fragment> fragmentWeakReference = fragmentMap.get(position + "");
        if (fragmentWeakReference != null) {
            Fragment fragment = fragmentWeakReference.get();
            if (fragment != null) {
                return fragment ;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}
