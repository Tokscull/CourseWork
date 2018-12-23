package api.entity;

import javafx.beans.property.*;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public  class Furniture implements  Externalizable {
    private final LongProperty idProducts = new SimpleLongProperty();
    private final StringProperty nameProduct = new SimpleStringProperty();
    private final StringProperty brand = new SimpleStringProperty();
    private final LongProperty  quantity = new SimpleLongProperty();
    private final FloatProperty purchasePrice = new SimpleFloatProperty();
    private final FloatProperty price = new SimpleFloatProperty();
    private final StringProperty note = new SimpleStringProperty();
    private final StringProperty nameProvider = new SimpleStringProperty();


    public long getIdProducts() {
        return idProducts.get();
    }
    public void setIdProducts(long idProducts) {
        this.idProducts.set(idProducts);
    }
    public String getNameProduct() {
        return nameProduct.get();
    }
    public void setNameProduct(String nameProduct) {
        this.nameProduct.set(nameProduct);
    }
    public String getBrand() {
        return brand.get();
    }
    public void setBrand(String brand) {
        this.brand.set(brand);
    }
    public long getQuantity() {
        return quantity.get();
    }
    public LongProperty quantityProperty() {
        return quantity;
    }
    public void setQuantity(long quantity) {
        this.quantity.set(quantity);
    }
    public float getPurchasePrice() {
        return purchasePrice.get();
    }
    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice.set(purchasePrice);
    }
    public float getPrice() {
        return price.get();
    }
    public void setPrice(float price) {
        this.price.set(price);
    }
    public String getNote() {
        return note.get();
    }
    public void setNote(String note) {
        this.note.set(note);
    }
    public String getNameProvider() {
        return nameProvider.get();
    }
    public void setNameProvider(String nameProvider) {
        this.nameProvider.set(nameProvider);
    }

    protected  Furniture getObject(){
        return new Furniture();
    }



    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getIdProducts());
        out.writeObject(this.getNameProduct());
        out.writeObject(this.getBrand());
        out.writeLong(this.getQuantity());
        out.writeFloat(this.getPurchasePrice());
        out.writeFloat(this.getPrice());
        out.writeObject(this.getNote());
        out.writeObject(this.getNameProvider());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setIdProducts(in.readLong());
        this.setNameProduct((String)in.readObject());
        this.setBrand((String)in.readObject());
        this.setQuantity(in.readLong());
        this.setPurchasePrice(in.readFloat());
        this.setPrice(in.readFloat());
        this.setNote((String)in.readObject());
        this.setNameProvider((String)in.readObject());
    }
}

