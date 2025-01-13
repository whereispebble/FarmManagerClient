/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import DTO.AnimalBean;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author Aitziber
 */
public interface IAnimalManager {
    public void updateAnimal(Object requestEntity) throws WebApplicationException;
    public <T> T getAnimalsByAnimalGroup(Class<T> responseType, String animalGroupName) throws WebApplicationException;
    public void createAnimal(Object requestEntity) throws WebApplicationException;
    public <T> T getAnimalsBySubespecies(Class<T> responseType, String subespecies) throws WebApplicationException;
    public <T> T getAnimalsByBirthdateTo(Class<T> responseType, String dateTo) throws WebApplicationException;
//    public void deleteAnimal(Object requestEntity) throws WebApplicationException;
    public void deleteAnimalById(String id) throws WebApplicationException;
    public <T> T getAnimalsByBirthdateFrom(Class<T> responseType, String dateFrom) throws WebApplicationException;
    public <T> T getAnimalByName(Class<T> responseType, String name) throws WebApplicationException;
    public <T> T getAnimalsByBirthdate(Class<T> responseType, String dateFrom, String dateTo) throws WebApplicationException;
    public void close();
}
