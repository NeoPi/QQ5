package com.neopi.qq5.v;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Author    :  NeoPi
 * Date      :  2017/11/27
 * Describe  :
 */

public class TextFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity()) ;
        textView.setText("Hello World !");
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new ViewGroup.LayoutParams(300,300));
        textView.setTextColor(ContextCompat.getColor(getActivity(),android.R.color.black));

        Bundle arguments = getArguments();

        if (arguments != null) {
            textView.setText(arguments.getString("title"));
        }

        return textView ;
    }
}
