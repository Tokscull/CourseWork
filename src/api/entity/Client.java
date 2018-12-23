package api.entity;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Client implements Externalizable {
    private  LongProperty idClient = new SimpleLongProperty();
    private  StringProperty surnameClient = new SimpleStringProperty();
    private  StringProperty nameClient = new SimpleStringProperty();
    private  StringProperty numberClient = new SimpleStringProperty();
    private  StringProperty countryClient = new SimpleStringProperty();
    private  StringProperty townClient = new SimpleStringProperty();
    private  StringProperty addressClient = new SimpleStringProperty();
    private  StringProperty noteClient = new SimpleStringProperty();
    private  StringProperty username = new SimpleStringProperty();
    private  StringProperty password = new SimpleStringProperty();

    public static class Builder {
        private Client newClient;

        public Builder() {
            newClient = new Client();
        }

        public Builder buildIdClient(long idClient) {
            newClient.idClient.set(idClient);
            return this;
        }

        public Builder buildSurnameClient(String surnameClient) {
            newClient.surnameClient.set(surnameClient);
            return this;
        }

        public Builder buildNameClient(String nameClient) {
            newClient.nameClient.set(nameClient);
            return this;
        }

        public Builder buildNumberClient(String numberClient) {
            newClient.numberClient.set(numberClient);
            return this;
        }

        public Builder buildCountryClient(String countryClient) {
            newClient.countryClient.set(countryClient);
            return this;
        }

        public Builder buildTownClient(String townClient) {
            newClient.townClient.set(townClient);
            return this;
        }

        public Builder buildAddressClient(String addressClient) {
            newClient.addressClient.set(addressClient);
            return this;
        }

        public Builder buildNoteClient(String noteClient) {
            newClient.noteClient.set(noteClient);
            return this;
        }

        public Builder buildUsername(String username) {
            newClient.username.set(username);
            return this;
        }

        public Builder buildPassword(String password) {
            newClient.password.set(password);
            return this;
        }

        public Client build(){
            return newClient;
        }

    }

    public long getIdClient() {
        return idClient.get();
    }
    public void setIdClient(long idClient) {
        this.idClient.set(idClient);
    }
    public String getSurnameClient() {
        return surnameClient.get();
    }
    public void setSurnameClient(String surnameClient) {
        this.surnameClient.set(surnameClient);
    }
    public String getNameClient() {
        return nameClient.get();
    }
    public void setNameClient(String nameClient) {
        this.nameClient.set(nameClient);
    }
    public String getNumberClient() {
        return numberClient.get();
    }
    public void setNumberClient(String numberClient) {
        this.numberClient.set(numberClient);
    }
    public String getCountryClient() {
        return countryClient.get();
    }
    public void setCountryClient(String countryClient) {
        this.countryClient.set(countryClient);
    }
    public String getTownClient() {
        return townClient.get();
    }
    public void setTownClient(String townClient) {
        this.townClient.set(townClient);
    }
    public String getAddressClient() {
        return addressClient.get();
    }
    public void setAddressClient(String addressClient) {
        this.addressClient.set(addressClient);
    }
    public String getNoteClient() {
        return noteClient.get();
    }
    public void setNoteClient(String noteClient) {
        this.noteClient.set(noteClient);
    }
    public String getUsername() {
        return username.get();
    }
    public void setUsername(String username) {
        this.username.set(username);
    }
    public String getPassword() {
        return password.get();
    }
    public void setPassword(String password) {
        this.password.set(password);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getIdClient());
        out.writeObject(this.getSurnameClient());
        out.writeObject(this.getNameClient());
        out.writeObject(this.getNumberClient());
        out.writeObject(this.getCountryClient());
        out.writeObject(this.getTownClient());
        out.writeObject(this.getAddressClient());
        out.writeObject(this.getNoteClient());
        out.writeObject(this.getUsername());
        out.writeObject(this.getPassword());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

        this.setIdClient((in.readLong()));
        this.setSurnameClient(((String) in.readObject()));
        this.setNameClient(((String) in.readObject()));
        this.setNumberClient(((String) in.readObject()));
        this.setCountryClient(((String) in.readObject()));
        this.setTownClient(((String) in.readObject()));
        this.setAddressClient(((String) in.readObject()));
        this.setNoteClient(((String) in.readObject()));
        this.setUsername(((String) in.readObject()));
        this.setPassword(((String) in.readObject()));
    }
}
