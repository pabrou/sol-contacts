package com.pabrou.sol.contacts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.pabrou.sol.contacts.models.Contact;
import com.pabrou.sol.contacts.R;

import java.util.List;

/**
 * Created by pablo on 11/7/15.
 */
public class ContactListAdapter extends ArrayAdapter<Contact> {

    private Context mContext;
    private ImageLoader mImageLoader;
    private List<Contact> mContacts;

    public ContactListAdapter(Context context, ImageLoader imageLoader, int resourceId, List<Contact> contacts) {
        super(context, resourceId, contacts);

        this.mContext = context;
        this.mContacts = contacts;
        this.mImageLoader = imageLoader;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContactHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.contact_list_item, parent, false);

            holder = new ContactHolder();
            holder.picture = (NetworkImageView) convertView.findViewById(R.id.picture);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.phoneNumber = (TextView) convertView.findViewById(R.id.phoneNumber);

            convertView.setTag(holder);
        }else {
           holder = (ContactHolder) convertView.getTag();
        }

        Contact contact = mContacts.get(position);

        holder.name.setText(contact.getName());
        holder.phoneNumber.setText(contact.getPhoneNumber().getHome());
        holder.picture.setImageUrl(contact.getSmallImageURL(), mImageLoader);
        holder.picture.setDefaultImageResId(R.drawable.user);
        holder.picture.setErrorImageResId(R.drawable.user_error);

        return convertView;
    }

    static class ContactHolder {
        NetworkImageView picture;
        TextView name;
        TextView phoneNumber;
    }

}
