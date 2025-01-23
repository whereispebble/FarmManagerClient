/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author InigoFreire
 */
public interface IProductManager {
    public void deleteProductById(String id) throws ClientErrorException;
    
    public void updateProduct(Object requestEntity) throws ClientErrorException;

    public void createProduct(Object requestEntity) throws ClientErrorException ;
    
    public <T> T searchByDate(Class<T> responseType, String date) throws ClientErrorException;
    
    public <T> T getProductByName(Class<T> responseType, String name) throws ClientErrorException;
    
    public void close();
}
