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
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by pablo on 11/7/15.
 */
public class ContactListRequest extends Request<List<Contact>> {

    private static final String WS_CONTACTS_URL = "https://solstice.applauncher.com/external/contacts.json";

    private final Response.Listener<List<Contact>> listener;

    public ContactListRequest(Response.Listener<List<Contact>> listener,
                              Response.ErrorListener errorListener) {

        super(Method.GET, WS_CONTACTS_URL, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<List<Contact>> parseNetworkResponse(NetworkResponse response) {

        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            ObjectMapper mapper = new ObjectMapper();
            List<Contact> list = mapper.readValue(json,
                    TypeFactory.defaultInstance().constructCollectionType(
                            List.class,
                            Contact.class));

            return Response.success(list, HttpHeaderParser.parseCacheHeaders(response));

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
    protected void deliverResponse(List<Contact> response) {
        listener.onResponse(response);
    }
}
