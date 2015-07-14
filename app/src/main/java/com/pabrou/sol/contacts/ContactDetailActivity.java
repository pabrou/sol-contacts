package com.pabrou.sol.contacts;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.pabrou.sol.contacts.models.Address;
import com.pabrou.sol.contacts.models.Contact;
import com.pabrou.sol.contacts.models.PhoneNumber;
import com.pabrou.sol.contacts.network.ContactRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ContactDetailActivity extends ActionBarActivity implements
        Response.ErrorListener,
        Response.Listener<Contact> {

    public static final String EXTRA_CONTACT = "contact_extra_key";

    private static final String SAVED_INST_CONTACT = "contactKey";

    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);

        // Set up the action bar.
        setSupportActionBar(toolBar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)actionBar.setDisplayHomeAsUpEnabled(true);

        // This is to avoid calling the endpoint again after a rotation
        // If it's the first time, then fetch the details
        if (savedInstanceState != null) {
            Contact value = savedInstanceState.getParcelable(SAVED_INST_CONTACT);
            if (value != null) {
                mContact = value;
                updateUI();
            }else{
                fetchContactDetail();
            }
        }else{
            fetchContactDetail();
        }
    }

    // Save contact to avoid calling the endpoint on rotation
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);

        savedState.putParcelable(SAVED_INST_CONTACT, mContact);
    }

    private void fetchContactDetail() {
        // Deserialize the parcelable contact
        mContact = getIntent().getParcelableExtra(EXTRA_CONTACT);

        // If there's a problema and the contact is null, then ABORT
        if (mContact == null) {
            this.setResult(RESULT_CANCELED);
            this.finish();
            return;
        }

        this.findViewById(R.id.status_updating).setVisibility(View.VISIBLE);
        this.findViewById(R.id.status_error).setVisibility(View.GONE);

        RequestQueue mRequestQueue = ContactsApplication.getInstance().getRequestQueue();
        Request req = new ContactRequest(mContact.getDetailsURL(), this, this);

        mRequestQueue.add(req);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Contact response) {
        //Fill the contact object with the data we didn't have
        mContact.setFavorite(response.isFavorite());
        mContact.setWebsite(response.getWebsite());
        mContact.setEmail(response.getEmail());
        mContact.setLargeImageURL(response.getLargeImageURL());
        mContact.setAddress(response.getAddress());

        updateUI();
    }

    private void updateUI() {
        ((TextView) this.findViewById(R.id.name)).setText(mContact.getName());
        ((TextView) this.findViewById(R.id.company)).setText(mContact.getCompany());

        PhoneNumber numbers = mContact.getPhoneNumber();
        if (numbers != null){
            if (TextUtils.isEmpty(numbers.getWork())) {
                this.findViewById(R.id.workPhoneNumberContainer).setVisibility(View.GONE);
            }else{
                ((TextView) this.findViewById(R.id.workPhoneNumber)).setText(numbers.getWork());
            }

            if (TextUtils.isEmpty(numbers.getHome())) {
                this.findViewById(R.id.homePhoneNumberContainer).setVisibility(View.GONE);
            }else{
                ((TextView) this.findViewById(R.id.homePhoneNumber)).setText(numbers.getHome());
            }

            if (TextUtils.isEmpty(numbers.getMobile())) {
                this.findViewById(R.id.mobilePhoneNumberContainer).setVisibility(View.GONE);
            }else{
                ((TextView) this.findViewById(R.id.mobilePhoneNumber)).setText(numbers.getMobile());
            }
        }

        Address address = mContact.getAddress();
        if (address != null){
            ((TextView) this.findViewById(R.id.addressStreet)).setText(address.getStreet());
            ((TextView) this.findViewById(R.id.addressCityState)).setText(address.getCityState());
        }

        ((TextView) this.findViewById(R.id.email)).setText(mContact.getEmail());

        Date birthday = mContact.getBirthdate();
        if (birthday != null) {
            DateFormat df = new SimpleDateFormat("MMMM d, yyyy", Locale.US);
            ((TextView) this.findViewById(R.id.birthday)).setText(df.format(birthday));
        }else{
            this.findViewById(R.id.birthdayContainer).setVisibility(View.GONE);
        }

        ((TextView) this.findViewById(R.id.website)).setText(mContact.getWebsite());
        this.findViewById(R.id.favorite).setVisibility(mContact.isFavorite() ? View.VISIBLE : View.GONE);

        NetworkImageView picture = (NetworkImageView)this.findViewById(R.id.picture);
        picture.setDefaultImageResId(R.drawable.user);
        picture.setErrorImageResId(R.drawable.user_error);
        picture.setImageUrl(
                mContact.getLargeImageURL(),
                ContactsApplication.getInstance().getImageLoader());

        this.findViewById(R.id.status_updating).setVisibility(View.GONE);
        this.findViewById(R.id.status_error).setVisibility(View.GONE);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        this.findViewById(R.id.status_updating).setVisibility(View.GONE);
        this.findViewById(R.id.status_error).setVisibility(View.VISIBLE);

        Toast errorMessage = Toast.makeText(this, R.string.error_loading_details, Toast.LENGTH_LONG);
        errorMessage.show();
    }
}
