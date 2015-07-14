package com.pabrou.sol.contacts;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pabrou.sol.contacts.adapters.ContactListAdapter;
import com.pabrou.sol.contacts.models.Contact;
import com.pabrou.sol.contacts.network.ContactListRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ContactListActivity extends ActionBarActivity implements
        AdapterView.OnItemClickListener,
        Response.ErrorListener,
        Response.Listener<List<Contact>> {

    private static final String SAVED_INST_CONTACT_LIST = "contactListKey";

    private List<Contact> mContactList;
    private ContactListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);

        // Set up the action bar.
        setSupportActionBar(toolBar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(false);

        // Set up the listview
        mContactList = new ArrayList<>();
        mAdapter = new ContactListAdapter(this,
                ContactsApplication.getInstance().getImageLoader(),
                R.layout.contact_list_item,
                mContactList);

        ListView listView = (ListView) findViewById(R.id.contact_list);
        listView.setEmptyView(findViewById(R.id.empty_list));
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

        // Read the savedInstanceState in order to get any previously saved contacts
        // This way we don't have to call the endpoint in every device rotation
        // If there are no contacts saved, then I fetch
        if (savedInstanceState != null) {
            Contact[] values = (Contact[])savedInstanceState.getParcelableArray(SAVED_INST_CONTACT_LIST);
            if (values != null) {
                mContactList.addAll(Arrays.asList(values));
                mAdapter.notifyDataSetChanged();
            }else{
                fetchContacts();
            }
        }else{
            fetchContacts();
        }
    }

    // Save contacts to avoid calling the endpoint on rotation
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);

        Contact[] values = new Contact[mContactList.size()];
        mContactList.toArray(values);
        savedState.putParcelableArray(SAVED_INST_CONTACT_LIST, values);
    }

    private void fetchContacts() {
        this.findViewById(R.id.status_updating).setVisibility(View.VISIBLE);

        // I get the requestQueue and create the request to get the list of contacts
        RequestQueue mRequestQueue = ContactsApplication.getInstance().getRequestQueue();
        Request req = new ContactListRequest(this, this);

        // add the request to the queue
        mRequestQueue.add(req);
    }

    @Override
    public void onResponse(List<Contact> response) {
        //Just in case, I clean the list
        mContactList.clear();
        mContactList.addAll(response);

        // Notify of changes in order to update the UI
        mAdapter.notifyDataSetChanged();
        this.findViewById(R.id.status_updating).setVisibility(View.GONE);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        this.findViewById(R.id.status_updating).setVisibility(View.GONE);

        Toast errorMessage = Toast.makeText(this, R.string.error_loading_contacts, Toast.LENGTH_LONG);
        errorMessage.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contact selContact = mContactList.get(position);

        // Since I need to show in the Details Activity some of the data that I got here,
        // I'll just pass the contact object as parcelable
        Intent intent = new Intent(this, ContactDetailActivity.class);
        intent.putExtra(ContactDetailActivity.EXTRA_CONTACT, selContact);
        this.startActivity(intent);
    }
}
