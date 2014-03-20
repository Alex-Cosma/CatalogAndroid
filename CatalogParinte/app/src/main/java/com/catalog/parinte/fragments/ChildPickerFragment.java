package com.catalog.parinte.fragments;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capricorn.ArcMenu;
import com.capricorn.RayMenu;
import com.catalog.parinte.R;
import com.catalog.parinte.activities.MainMenuActivity;

import org.w3c.dom.Text;

/**
 * Created by Alex on 3/20/14.
 */
public class ChildPickerFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final int[] ITEM_DRAWABLES = {R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};

    private Activity act;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ChildPickerFragment newInstance() {
        ChildPickerFragment fragment = new ChildPickerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 1);
        fragment.setArguments(args);
        return fragment;
    }

    public ChildPickerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_child_picker, container, false);
        initUI(rootView);
        return rootView;
    }

    private void initUI(View rootView) {
        act = getActivity();

        final ArcMenu arcMenu = (ArcMenu) rootView.findViewById(R.id.arc_menu);

        initArcMenu(arcMenu, ITEM_DRAWABLES);

        RayMenu rayMenu = (RayMenu) rootView.findViewById(R.id.ray_menu);
        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {

            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View arcItem = inflater.inflate(R.layout.arcitem_child, null);


            ImageView ivStudent = (ImageView) arcItem.findViewById(R.id.ivStudent);
            ivStudent.setImageResource(ITEM_DRAWABLES[i]);

            TextView tvStudent = (TextView) arcItem.findViewById(R.id.tvStudentName);
            tvStudent.setText("Student " + i);

            final int position = i;

            rayMenu.addItem(arcItem, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(act.getApplicationContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                    arcMenu.showMenuWithAnimation();
                }
            });// Add a menu item
        }

    }


    private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View arcItem = inflater.inflate(R.layout.arcitem_child, null);


            ImageView ivStudent = (ImageView) arcItem.findViewById(R.id.ivStudent);
            ivStudent.setImageResource(ITEM_DRAWABLES[i]);

            TextView tvStudent = (TextView) arcItem.findViewById(R.id.tvStudentName);
            tvStudent.setText("Student " + i);

            final int position = i;
            menu.addItem(arcItem, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(act.getApplicationContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainMenuActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}