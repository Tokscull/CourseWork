package api.entity;

import javafx.beans.property.*;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;

public class Order implements Externalizable {
    private final LongProperty idOrder = new SimpleLongProperty();
    private final StringProperty nameFurniture = new SimpleStringProperty();
    private final StringProperty noteFurniture = new SimpleStringProperty();
    private final LongProperty quantityFurniture = new SimpleLongProperty();
    private final FloatProperty priceFurniture = new SimpleFloatProperty();
    private final FloatProperty priceDelivery = new SimpleFloatProperty();
    private final LongProperty idClient = new SimpleLongProperty();
    private final LongProperty idStaff = new SimpleLongProperty();
    private final ObjectProperty<Date> dateOrder = new SimpleObjectProperty();


    public long getIdOrder() {
        return idOrder.get();
    }
    public void setIdOrder(long idOrder) {
        this.idOrder.set(idOrder);
    }
    public String getNameFurniture() {
        return nameFurniture.get();
    }
    public void setNameFurniture(String nameFurniture) {
        this.nameFurniture.set(nameFurniture);
    }
    public String getNoteFurniture() {
        return noteFurniture.get();
    }
    public void setNoteFurniture(String noteFurniture) {
        this.noteFurniture.set(noteFurniture);
    }
    public long getQuantityFurniture() {
        return quantityFurniture.get();
    }
    public void setQuantityFurniture(long quantityFurniture) {
        this.quantityFurniture.set(quantityFurniture);
    }
    public float getPriceFurniture() {
        return priceFurniture.get();
    }
    public void setPriceFurniture(float priceFurniture) {
        this.priceFurniture.set(priceFurniture);
    }
    public float getPriceDelivery() {
        return priceDelivery.get();
    }
    public void setPriceDelivery(float priceDelivery) {
        this.priceDelivery.set(priceDelivery);
    }
    public long getIdClient() {
        return idClient.get();
    }
    public void setIdClient(long idClient) {
        this.idClient.set(idClient);
    }
    public long getIdStaff() {
        return idStaff.get();
    }
    public void setIdStaff(long idStaff) {
        this.idStaff.set(idStaff);
    }
    public Date getDateOrder() {
        return dateOrder.get();
    }
    public void setDateOrder(Date dateOrder) {
        this.dateOrder.set(dateOrder);
    }



    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getIdOrder());
        out.writeObject(this.getNameFurniture());
        out.writeObject(this.getNoteFurniture());
        out.writeLong(this.getQuantityFurniture());
        out.writeFloat(this.getPriceFurniture());
        out.writeFloat(this.getPriceDelivery());
        out.writeLong(this.getIdClient());
        out.writeLong(this.getIdStaff());
        out.writeObject(this.getDateOrder());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setIdOrder(in.readLong());
        this.setNameFurniture((String)in.readObject());
        this.setNoteFurniture((String)in.readObject());
        this.setQuantityFurniture(in.readLong());
        this.setPriceFurniture(in.readFloat());
        this.setPriceDelivery(in.readFloat());
        this.setIdClient(in.readLong());
        this.setIdStaff(in.readLong());
        this.setDateOrder((Date) in.readObject());
    }
}
