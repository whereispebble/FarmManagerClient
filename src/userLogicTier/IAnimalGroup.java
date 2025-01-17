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
    
    //CREATE
    public void createAnimalGroup(Object requestEntity) throws WebApplicationException;

    //GETS
    public <T> T getAnimalGroupByName(Class<T> responseType, String name, String managerId) throws WebApplicationException;

    public <T> T getAnimalGroupsByManager(Class<T> responseType, String managerId) throws WebApplicationException;
    
    // public <T> T getAnimalGroups(Class<T> responseType) throws WebApplicationException;
    
    //UPDATE
    public void updateAnimalGroup(Object requestEntity) throws WebApplicationException;

    //DELETE
    public void deleteAnimalGroupById(String id) throws WebApplicationException;
    
    // public void deleteAnimalGroup(Object requestEntity) throws WebApplicationException;
    
    public void close();
}
