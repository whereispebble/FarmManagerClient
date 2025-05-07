/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents the composite key for a consumption record.
 * @author Pablo
 */
@XmlRootElement(name = "consumesId")
public class ConsumesIdBean implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private Long animalGroupId;

    private Long productId;


    public ConsumesIdBean() {
    }


    public ConsumesIdBean(Long productId, Long animalGroupId) {
        this.productId = productId;
        this.animalGroupId = animalGroupId;
    }


    public Long getAnimalGroupId() {
        return animalGroupId;
    }

 
    public void setAnimalGroupId(Long animalGroupId) {
        this.animalGroupId = animalGroupId;
    }

    public Long getProductId() {
        return productId;
    }


    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumesIdBean that = (ConsumesIdBean) o;
        return Objects.equals(animalGroupId, that.animalGroupId) &&
               Objects.equals(productId, that.productId);
    }

  
    @Override
    public int hashCode() {
        return Objects.hash(animalGroupId, productId);
    }
}
