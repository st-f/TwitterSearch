package net.stf.twittersearch;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by st-f on 07/07/2016
 */

public class ListAdapter extends ArrayAdapter<ListAdapter.ListViewItemModel> {

    private final Context context;
    private final List<ListViewItemModel> data;
    private final ImageLoader mImageLoader;

    public ListAdapter(Context context, int resource, List<ListViewItemModel> objects) {
        super(context, resource, objects);
        this.context = context;
        data = objects;
        mImageLoader = VolleySingleton.getInstance(context).getImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            holder = new ViewHolder();
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageContainer = (RelativeLayout) convertView.findViewById(R.id.list_item_image_container);
        final boolean isImageURLEmpty = TextUtils.isEmpty(data.get(position).imageURL);
        holder.imageContainer.setVisibility(isImageURLEmpty ? View.GONE : View.VISIBLE);
        if(!isImageURLEmpty) {
            holder.imageView = (NetworkImageView) convertView.findViewById(R.id.list_item_imageview);
            holder.imageView.setImageUrl(data.get(position).imageURL, mImageLoader);
            holder.imageView.setTag(data.get(position).imageURL);
        }
        holder.profileImageView = (NetworkImageView) convertView.findViewById(R.id.list_item_profile_imageview);
        holder.profileImageView.setImageUrl(data.get(position).profileImageURL, mImageLoader);
        holder.profileImageView.setTag(data.get(position).profileImageURL);
        holder.profileNameText = (TextView) convertView.findViewById(R.id.list_item_profile_name_tv);
        holder.profileNameText.setText(data.get(position).profileName);
        holder.textView = (TextView) convertView.findViewById(R.id.list_item_text);
        holder.textView.setText(data.get(position).text);
        convertView.setTag(holder);
        return convertView;
    }

    static class ViewHolder {

        private NetworkImageView profileImageView;
        private TextView profileNameText;
        private TextView textView;
        private RelativeLayout imageContainer;
        private NetworkImageView imageView;
    }

    public static class ListViewItemModel {

        public String profileName;
        public String profileImageURL;
        public String imageURL;
        public String text;
    }
}

