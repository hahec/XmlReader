package entity;

import Annotation.XmlAttr;
import Annotation.XmlAttrImplicit;

import java.util.ArrayList;
import java.util.List;

public class RspPerson {
    @XmlAttr("message")
    private String rspState;
    @XmlAttrImplicit("ryxxlist")
    private List<Person> personlist;

    public String getRspState() {
        return rspState;
    }

    public void setRspState(String rspState) {
        this.rspState = rspState;
    }

    public List<Person> getPersonlist() {
        return personlist;
    }

    public void setPersonlist(List<Person> personlist) {
        this.personlist = personlist;
    }
}
