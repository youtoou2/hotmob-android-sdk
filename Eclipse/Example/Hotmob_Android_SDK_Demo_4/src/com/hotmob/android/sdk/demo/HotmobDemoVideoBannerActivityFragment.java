package com.hotmob.android.sdk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HotmobDemoVideoBannerActivityFragment extends Fragment{
	protected final String TAG = this.getClass().getName();

    private ListView mListView;

    private ArrayList<View> mList;
    private CellArrayAdapter mCellArrayAdapter;

    private final int mBannerPosition = 3;
    private final int mCellNum = 50;
    
    private View mBannerView;
   
    public HotmobDemoVideoBannerActivityFragment(){
    	
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	
    	View view = inflater.inflate(R.layout.fragment_hotmob_demo_video_banner, container, false);

        mListView = (ListView)view.findViewById(R.id.hotmob_demo_video_banner_list);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                updateBannerPosition();
            }
        });
        
        this.createList();
    	return view;
    }
    
    private void createList() {
        if (mList == null) {
            mList = new ArrayList<View>();
        }

        for (int i=0; i<mCellNum; i++) {
            if (i == mBannerPosition) {
                createBannerCell();
            } else {
                createNormalCell(i);
            }
        }

        mCellArrayAdapter = new CellArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mCellArrayAdapter);
    }
    
    private void createNormalCell(int position) {
        //1.) Add Title View
        LayoutInflater titleInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View titleView = titleInflater.inflate(R.layout.listview_cell_item, null);
        TextView cellTextView = (TextView)titleView.findViewById(R.id.listview_cell_item_textview);
        cellTextView.setText("Item #" + position);
        mList.add(titleView);
    }
    
    private void createBannerCell() {
        final String adCode = "hotmob_uat_android_image_inapp_banner";

        LayoutInflater bannerInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bannerView = bannerInflater.inflate(R.layout.listview_banner_cell_item, null);
        final LinearLayout bannerLayout = (LinearLayout)bannerView.findViewById(R.id.list_banner_cell_layout);

        HotmobManagerListener listener = new HotmobManagerListener() {
            public void didLoadBanner(View bannerView) {
                mBannerView = bannerView;
                bannerLayout.addView(bannerView);
            }

            public void openInternalCallback(View bannerView, String url) {
                super.openInternalCallback(bannerView, url);

            }

            public void onResizeBanner(View bannerView) {
                super.onResizeBanner(bannerView);
            }
        };

        HotmobManager.getBanner(getActivity(), listener, HotmobManager.getScreenWidth(getActivity()), "DemoVideoBannerActivityBanner", adCode);
        mList.add(bannerView);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        HotmobManager.destroyBanner(mBannerView);
    }
    
    private void updateBannerPosition() {
        HotmobManager.updateBannerPosition(this.getActivity());
    }
    
    private class CellArrayAdapter extends ArrayAdapter<View> {
        private HashMap<Integer, View> mIdMap;

        public CellArrayAdapter(Context context, int textViewResourceId, List<View> objects) {
            super(context, textViewResourceId, objects);

            mIdMap = new HashMap<Integer, View>();

            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(i, objects.get(i));
            }
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = mIdMap.get(position);
            return rowView;
        }
    }
}
