package jp.nadai.twitterapplication.model;

import jp.nadai.twitterapplication.R;
import twitter4j.DirectMessage;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

public class DMline extends ArrayAdapter<DirectMessage>{
    private LayoutInflater mInflater;
    public DMline(Context context)  {
        super(context, android.R.layout.simple_list_item_1);
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_layout, null);
        }
        DirectMessage item = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(item.getSender().getName());
 
        TextView screenName = (TextView) convertView.findViewById(R.id.screen_name);
        screenName.setText("@" + item.getSender().getScreenName());
 
        TextView text = (TextView) convertView.findViewById(R.id.text);
        text.setText(item.getText());
 
        SmartImageView icon = (SmartImageView) convertView.findViewById(R.id.icon);
        icon.setImageUrl(item.getSender().getProfileImageURL());
        return convertView;
    }
}
 