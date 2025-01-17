/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Ander
 */
public interface IAnimalGroup {
    public <T> T getAnimalGroupsByName(Class<T> responseType, String name) throws WebApplicationException;

    public void updateAnimalGroup(Object requestEntity) throws WebApplicationException;

    public void createAnimalGroup(Object requestEntity) throws WebApplicationException;

    public void deleteAnimalGroup(Object requestEntity) throws WebApplicationException;
    
    public void close();
}
