package jp.nadai.twitterapplication.model;

import jp.nadai.twitterapplication.R;
import twitter4j.Status;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

public class Timeline extends ArrayAdapter<Status> {
    private LayoutInflater mInflater;
    public Timeline(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_layout, null);
        }
        Status item = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(item.getUser().getName());
        name.setTextColor(0xff888888);

        TextView screenName = (TextView) convertView.findViewById(R.id.screen_name);
        screenName.setText("@" + item.getUser().getScreenName());
        screenName.setTextColor(0xff888888);

        TextView text = (TextView) convertView.findViewById(R.id.text);
        text.setText(item.getText());

        SmartImageView icon = (SmartImageView) convertView.findViewById(R.id.icon);
        icon.setImageUrl(item.getUser().getProfileImageURL());
        return convertView;
        
    }
}