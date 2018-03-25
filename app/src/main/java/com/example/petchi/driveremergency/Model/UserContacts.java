package com.example.petchi.driveremergency.Model;

/**
 * Created by petch on 19-03-2018.
 */

public class UserContacts {
    String name1,name2,name3;
    long mob1, mob2,mob3;
    String rel1,rel2,rel3;
    String uuid;

    public UserContacts(String pname1, String pname2, String pname3, long pmob1, long pmob2, long pmob3, String prel1, String prel2, String prel3, String puuid) {
        name1 = pname1;
        name2 = pname2;
        name3 = pname3;
        mob1 = pmob1;
        mob2 = pmob2;
        mob3 = pmob3;
        rel1 = prel1;
        rel2 = prel2;
        rel3 = prel3;
        uuid = puuid;
    }

    public UserContacts() {
    }

    public String getPname1() {
        return name1;
    }

    public void setPname1(String pname1) {
        name1 = pname1;
    }

    public String getPname2() {
        return name2;
    }

    public void setPname2(String pname2) {
        name2 = pname2;
    }

    public String getPname3() {
        return name3;
    }

    public void setPname3(String pname3) {
        name3 = pname3;
    }

    public long getPmob1() {
        return mob1;
    }

    public void setPmob1(long pmob1) {
        mob1 = pmob1;
    }

    public long getPmob2() {
        return mob2;
    }

    public void setPmob2(long pmob2) {
        mob2 = pmob2;
    }

    public long getPmob3() {
        return mob3;
    }

    public void setPmob3(long pmob3) {
        mob3 = pmob3;
    }

    public String getRel1() {
        return rel1;
    }

    public void setRel1(String prel1) {
        rel1 = prel1;
    }

    public String getRel2() {
        return rel2;
    }

    public void setRel2(String prel2) {
        rel2 = prel2;
    }

    public String getRel3() {
        return rel3;
    }

    public void setRel3(String prel3) {
        rel3 = prel3;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String puuid) {
        uuid = puuid;
    }
}

