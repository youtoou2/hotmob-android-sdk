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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class HotmobDemoPopupActivityFragment extends Fragment{
	private ListView mListView;

    private ArrayList<View> mList;
    private CellArrayAdapter mCellArrayAdapter;

    private LinearLayout mBannerLayout;
    
    public HotmobDemoPopupActivityFragment(){
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	View view = inflater.inflate(R.layout.fragment_hotmob_demo_popup, container, false);

        mListView = (ListView)view.findViewById(R.id.hotmob_demo_popup_list);
        mBannerLayout = (LinearLayout)view.findViewById(R.id.hotmob_example_main_banner_layout);

        this.createList();
    	return view;
    }
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    }
    
    private void createList() {
    	String[] values = new String[]{
                "Image Popup",
                "Video Popup"
        };
    	
    	if (mList == null) {
            mList = new ArrayList<View>();
        }

        for (int i = 0; i < values.length; i++) {
            createListItem(values[i]);
        }
        
        mCellArrayAdapter = new CellArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mCellArrayAdapter);
        
        mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String adcode;
				if (position == 1) {
					adcode = "hotmob_uat_android_video_inapp_popup";
				}else{
					adcode = "hotmob_uat_android_image_inapp_popup";
				}
				
				HotmobManagerListener listener = new HotmobManagerListener() {
                    public void didLoadBanner(View bannerView) {
                        super.didLoadBanner(bannerView);
                    }

                    public void didShowBanner(View bannerView) {
                        super.didShowBanner(bannerView);
                    }

                    public void didHideBanner(View bannerView) {
                        super.didHideBanner(bannerView);
                    }

                    public void didLoadFailed(View bannerView) {
                        super.didLoadFailed(bannerView);
                    }

                    public void didClick(View bannerView) {
                        super.didClick(bannerView);
                    }

                    public void didShowInAppBrowser(View bannerView) {
                        super.didShowInAppBrowser(bannerView);
                    }

                    public void didCloseInAppBrowser(View bannerView) {
                        super.didCloseInAppBrowser(bannerView);
                    }

                    public void openNoAdCallback(View bannerView) {
                        super.openNoAdCallback(bannerView);
                    }

                    public void openInternalCallback(View bannerView, String url) {
                        super.openInternalCallback(bannerView, url);

                    }

                    public void hotmobBannerIsReadyChangeSoundSettings(boolean isSoundEnable) {
                        
                    }
                };
                
                HotmobManager.getPopup(HotmobDemoPopupActivityFragment.this.getActivity(), listener, "PopupActivityPopup", adcode, false);
			}
		});
    }
    
    private void createListItem(String value) {
        //1.) Add Title View
        LayoutInflater titleInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View titleView = titleInflater.inflate(R.layout.listview_cell_item, null);
        TextView cellTextView = (TextView)titleView.findViewById(R.id.listview_cell_item_textview);
        cellTextView.setText(value);
        mList.add(titleView);
    }
    
    private class CellArrayAdapter extends ArrayAdapter<View> {
        private HashMap<Integer, View> mIdMap;

        public CellArrayAdapter(Context context, int textViewResourceId,List<View> objects) {
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
