package com.pabrou.sol.contacts.network.deserializer;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date>
{
    @Override
    public Date deserialize(JsonParser jsonparser,
            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {

        String unixSeconds = jsonparser.getText();

        //return null if the time is empty
        if (unixSeconds.isEmpty())
        	return null;

        int timestamp = Integer.parseInt(unixSeconds);
        return new Date((long)timestamp*1000);
    }

}
