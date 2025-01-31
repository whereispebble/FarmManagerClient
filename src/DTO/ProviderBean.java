/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import DTO.UserBean;

/**
 *
 * @author InigoFreire
 */
@XmlRootElement(name = "providerEntity")
public class ProviderBean extends UserBean implements Serializable {

    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER)
    private List<ProductBean> products;

    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public String getName() {
        return super.getName(); // Obtiene el nombre del proveedor
    }

    @Override
    public void setName(String name) {
        super.setName(name); // Establece el nombre del proveedor
    }

    public List<ProductBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBean> products) {
        this.products = products;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductBean)) {
            return false;
        }
        ProductBean other = (ProductBean) object;
        if ((super.id == null && other.id != null) || (super.id != null && !super.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Provider[ id=" + id + " ]";
    }

}
