package com.pabrou.sol.contacts.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.pabrou.sol.contacts.models.Contact;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by pablo on 11/7/15.
 */
public class ContactRequest extends Request<Contact> {

    private final Response.Listener<Contact> listener;

    public ContactRequest(String url, Response.Listener<Contact> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);

        this.listener = listener;
    }

    @Override
    protected Response<Contact> parseNetworkResponse(NetworkResponse response) {

        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            ObjectMapper mapper = new ObjectMapper();
            Contact contact = mapper.readValue(json, Contact.class);

            return Response.success(contact, HttpHeaderParser.parseCacheHeaders(response));

        } catch (JsonMappingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            return Response.error(new ParseError(e));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void deliverResponse(Contact response) {
        listener.onResponse(response);
    }
}
