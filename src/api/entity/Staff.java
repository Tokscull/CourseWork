package api.entity;

import javafx.beans.property.*;

import java.io.*;
import java.time.LocalDate;

public class Staff implements Externalizable {
    private final LongProperty idStaff = new SimpleLongProperty();
    private final StringProperty surnameStaff = new SimpleStringProperty();
    private final StringProperty nameStaff = new SimpleStringProperty();
    private final StringProperty positionStaff = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> birthDayStaff = new SimpleObjectProperty();
    private final ObjectProperty<LocalDate> employmentDayStaff = new SimpleObjectProperty();
    private final StringProperty numberStaff = new SimpleStringProperty();
    private final StringProperty noteStaff = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();


    public long getIdStaff() {
        return idStaff.get();
    }
    public void setIdStaff(long idStaff) {
        this.idStaff.set(idStaff);
    }
    public String getSurnameStaff() {
        return surnameStaff.get();
    }
    public void setSurnameStaff(String surnameStaff) {
        this.surnameStaff.set(surnameStaff);
    }
    public String getNameStaff() {
        return nameStaff.get();
    }
    public void setNameStaff(String nameStaff) {
        this.nameStaff.set(nameStaff);
    }
    public String getPositionStaff() {
        return positionStaff.get();
    }
    public void setPositionStaff(String positionStaff) {
        this.positionStaff.set(positionStaff);
    }
    public LocalDate getBirthDayStaff() {
        return birthDayStaff.get();
    }
    public void setBirthDayStaff(LocalDate birthDayStaff) {
        this.birthDayStaff.set(birthDayStaff);
    }
    public LocalDate getEmploymentDayStaff() {
        return employmentDayStaff.get();
    }
    public void setEmploymentDayStaff(LocalDate employmentDayStaff) {
        this.employmentDayStaff.set(employmentDayStaff);
    }
    public String getNumberStaff() {
        return numberStaff.get();
    }
    public void setNumberStaff(String numberStaff) {
        this.numberStaff.set(numberStaff);
    }
    public String getNoteStaff() {
        return noteStaff.get();
    }
    public void setNoteStaff(String noteStaff) {
        this.noteStaff.set(noteStaff);
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
        out.writeLong(this.getIdStaff());
        out.writeObject(this.getSurnameStaff());
        out.writeObject(this.getNameStaff());
        out.writeObject(this.getPositionStaff());
        out.writeObject(this.getBirthDayStaff());
        out.writeObject(this.getEmploymentDayStaff());
        out.writeObject(this.getNumberStaff());
        out.writeObject(this.getNoteStaff());
        out.writeObject(this.getUsername());
        out.writeObject(this.getPassword());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setIdStaff(in.readLong());
        this.setSurnameStaff((String)in.readObject());
        this.setNameStaff((String)in.readObject());
        this.setPositionStaff((String)in.readObject());
        this.setBirthDayStaff((LocalDate)in.readObject());
        this.setEmploymentDayStaff((LocalDate)in.readObject());
        this.setNumberStaff((String)in.readObject());
        this.setNoteStaff((String)in.readObject());
        this.setUsername((String)in.readObject());
        this.setPassword((String)in.readObject());
    }
}
