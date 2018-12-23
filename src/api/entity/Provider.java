package api.entity;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Provider implements Externalizable {
    private final LongProperty idProviders = new SimpleLongProperty();
    private final StringProperty nameProvider = new SimpleStringProperty();
    private final StringProperty country = new SimpleStringProperty();
    private final StringProperty town = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty contactPerson = new SimpleStringProperty();
    private final StringProperty contactNumber = new SimpleStringProperty();
    private final StringProperty note = new SimpleStringProperty();


    public long getIdProviders() {
        return idProviders.get();
    }
    public void setIdProviders(long idProviders) {
        this.idProviders.set(idProviders);
    }
    public String getNameProvider() {
        return nameProvider.get();
    }
    public void setNameProvider(String nameProvider) {
        this.nameProvider.set(nameProvider);
    }
    public String getCountry() {
        return country.get();
    }
    public void setCountry(String country) {
        this.country.set(country);
    }
    public String getTown() {
        return town.get();
    }
    public void setTown(String town) {
        this.town.set(town);
    }
    public String getAddress() {
        return address.get();
    }
    public void setAddress(String address) {
        this.address.set(address);
    }
    public String getContactPerson() {
        return contactPerson.get();
    }
    public void setContactPerson(String contactPerson) {
        this.contactPerson.set(contactPerson);
    }
    public String getContactNumber() {
        return contactNumber.get();
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber.set(contactNumber);
    }
    public String getNote() {
        return note.get();
    }
    public void setNote(String note) {
        this.note.set(note);
    }



    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getIdProviders());
        out.writeObject(this.getNameProvider());
        out.writeObject(this.getCountry());
        out.writeObject(this.getTown());
        out.writeObject(this.getAddress());
        out.writeObject(this.getContactPerson());
        out.writeObject(this.getContactNumber());
        out.writeObject(this.getNote());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setIdProviders(in.readLong());
        this.setNameProvider((String)in.readObject());
        this.setCountry((String)in.readObject());
        this.setTown((String)in.readObject());
        this.setAddress((String)in.readObject());
        this.setContactPerson((String)in.readObject());
        this.setContactNumber((String)in.readObject());
        this.setNote((String)in.readObject());
    }
}
