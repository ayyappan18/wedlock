package com.ayyappan.androidapp.wedlock.menudrawer.adapater;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions;
import com.ayyappan.androidapp.wedlock.menudrawer.utils.IconDecoder;

/**
 * Created by Ayyappan on 02/11/2015.
 */
public class MenuDrawerListAdapter extends BaseExpandableListAdapter {

    private Context _context;

    //Menu Options
    //Menu header titles
    private List<String> _listDataHeader;
    //Menu child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET =
            {android.R.attr.state_expanded};
    private static final int[][] GROUP_STATE_SETS = {
            EMPTY_STATE_SET, // 0
            GROUP_EXPANDED_STATE_SET // 1
    };
    public MenuDrawerListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.menu_list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.menu_list_group, null);
        }

       View ind = convertView.findViewById( R.id.explist_indicator);
        if( ind != null ) {
            ImageView indicator = (ImageView) ind;
            if (getChildrenCount(groupPosition) == 0) {
                indicator.setVisibility(View.INVISIBLE);
            } else {
                indicator.setVisibility(View.VISIBLE);
                indicator.setImageResource(isExpanded ? R.drawable.expander_ic_maximized : R.drawable.expander_ic_minimized );

              /*  int stateSetIndex = (isExpanded ? 1 : 0);
                Drawable drawable = indicator.getDrawable();
                drawable.setState(GROUP_STATE_SETS[stateSetIndex]);*/
            }
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle);

        ImageView groupMenuIcon = (ImageView)convertView.findViewById(R.id.lbListIcon);
        int image = getImageForGroup(headerTitle);
        groupMenuIcon.setImageBitmap(IconDecoder.decodeSampledBitmapFromResource(_context.getResources(), image, 48, 48));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private int getImageForGroup(String header){
        switch(header){
            case MenuOptions.HOME : return R.drawable.home_icon;
            case MenuOptions.BIOGRAPHY : return R.drawable.bio_icon;
            case MenuOptions.GALLERY : return R.drawable.gallery_icon;
            case MenuOptions.ENTERTAINMENT : return R.drawable.entertainment_icon;
            case MenuOptions.INVITATION : return R.drawable.invitation_icon;
            case MenuOptions.EVENTS : return R.drawable.event_icon;
            case MenuOptions.ABOUT : return R.drawable.about_icon;
            default : return R.drawable. about_icon;
        }
    }

}

