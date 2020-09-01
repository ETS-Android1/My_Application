package com.example.myapplication;

public class YourBookingPOJO {

    String userid;
    String agentname;
    String agentaddress;
    String agentcontact;
    String username;
    String useraddress;
    String useremail;
    String usercontact;
    String from;
    String to;
    String date;
    String time;
    String bookingpref;
    String vtime;
    String vtype;
    String vsize;
    String bookingdate;
    String bookingid;
    String travelemail;

    public YourBookingPOJO(){}

    public YourBookingPOJO(String travelemail, String bookingid, String bookingdate, String agentname, String agentaddress, String agentcontact, String username,
                           String useraddress, String usercontact, String useremail, String from, String to,
                           String date, String time, String bookingpref, String vtype, String vsize,
                           String vtime, String userid) {
        this.travelemail=travelemail;
        this.bookingid=bookingid;
        this.bookingdate=bookingdate;
        this.agentname = agentname;
        this.agentaddress = agentaddress;
        this.agentcontact = agentcontact;
        this.username = username;
        this.useraddress = useraddress;
        this.usercontact = usercontact;
        this.useremail = useremail;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.bookingpref = bookingpref;
        this.vtype = vtype;
        this.vsize = vsize;
        this.vtime = vtime;
        this.userid = userid;
    }

    public String getTravelemail(){return travelemail;}
    public String getBookingid(){return bookingid;}
    public String getBookingdate(){return bookingdate;}
    public String getAgentaddress() {
        return agentaddress;
    }

    public String getAgentcontact() {
        return agentcontact;
    }

    public String getAgentname() {
        return agentname;
    }

    public String getVtime() {
        return vtime;
    }

    public String getVsize() {
        return vsize;
    }

    public String getVtype() {
        return vtype;
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getTime() {
        return time;
    }

    public String getTo() {
        return to;
    }

    public String getBookingpref() {
        return bookingpref;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public String getUsercontact() {
        return usercontact;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }


}
