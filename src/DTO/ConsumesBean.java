/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**

* DTO used in UI and client side for representing a Consume entity .
 * It is also used as data model for a TableView in the UI.
 *
 * @author Pablo
 */
@XmlRootElement(name = "consumes")
public class ConsumesBean implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private ConsumesIdBean consumesId;

    private AnimalGroupBean animalGroup;

    private ProductBean product;

    private Date date;

    private Float consumeAmount;

    public ConsumesIdBean getConsumesId() {
        return consumesId;
    }

 
    public void setConsumesId(ConsumesIdBean id) {
        this.consumesId = id;
    }


    public Float getConsumeAmount() {
        return consumeAmount;
    }


    public void setConsumeAmount(Float consume) {
        this.consumeAmount = consume;
    }


    public AnimalGroupBean getAnimalGroup() {
        return animalGroup;
    }

    public void setAnimalGroup(AnimalGroupBean animalGroup) {
        this.animalGroup = animalGroup;
    }


    public ProductBean getProduct() {
        return product;
    }


    public void setProduct(ProductBean product) {
        this.product = product;
    }


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public ConsumesBean clone() throws CloneNotSupportedException {
        return (ConsumesBean) super.clone();
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consumesId != null ? consumesId.hashCode() : 0);
        return hash;
    }

   
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConsumesBean)) {
            return false;
        }
        ConsumesBean other = (ConsumesBean) object;
        return (this.consumesId != null || other.consumesId == null) &&
               (this.consumesId == null || this.consumesId.equals(other.consumesId));
    }

   
    @Override
    public String toString() {
        return "entities.Consumes[ consumeId=" + consumesId + " consumeAmount=" + consumeAmount + " ]";
    }
}
