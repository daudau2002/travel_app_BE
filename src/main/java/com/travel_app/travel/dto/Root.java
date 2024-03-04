package com.travel_app.travel.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
@Data
public class Root{
    public String at_hash;
    public String sub;
    public boolean email_verified;
    public String iss;
    public String given_name;
    public String locale;
    public String nonce;
    public String picture;
    public ArrayList<String> aud;
    public String azp;
    public String name;
    public Date exp;
    public String family_name;
    public Date iat;
    public String email;
}