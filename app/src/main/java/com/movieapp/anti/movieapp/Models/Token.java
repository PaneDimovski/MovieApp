package com.movieapp.anti.movieapp.Models;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Anti on 2/15/2018.
 */

public class Token implements Serializable {

    public Boolean success;
    public String expires_at;
    public String request_token;
    public String session_id;


    public Token(String request_token) {

        this.request_token = request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    public String getRequest_token() {
        return request_token;
    }

    public Boolean isExpired () {

          DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
          df.setTimeZone(TimeZone.getTimeZone("GMT"));
          Date date= null;// converting String to date
          try {
              date = df.parse(expires_at);
          } catch (ParseException e) {
              e.printStackTrace();
          }
          Date today=new Date();

          return today.after(date);
      }

}
