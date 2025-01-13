/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import DTO.AnimalBean;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:AnimalREST [animal]<br>
 * USAGE:
 * <pre>
 *        AnimalRESTClient client = new AnimalRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Usuario
 */
public class AnimalRESTClient implements IAnimalManager{

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/FarmManagerServer/webresources";
    

    public AnimalRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("animal");
    }

    public void updateAnimal(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), AnimalBean.class);
    }

    public <T> T getAnimalsByAnimalGroup(Class<T> responseType, String animalGroupName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("group/{0}", new Object[]{animalGroupName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void createAnimal(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), AnimalBean.class);
    }

    public <T> T getAnimalsBySubespecies(Class<T> responseType, String subespecies) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("subespecies/{0}", new Object[]{subespecies}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T getAnimalsByBirthdateTo(Class<T> responseType, String dateTo) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("to/{0}", new Object[]{dateTo}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

//    public void deleteAnimal(Object requestEntity) throws WebApplicationException {
//        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).delete(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
//    }

    public void deleteAnimalById(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("delete/{0}", new Object[]{id})).request().delete(AnimalBean.class);
    }

    public <T> T getAnimalsByBirthdateFrom(Class<T> responseType, String dateFrom) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("from/{0}", new Object[]{dateFrom}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T getAnimalByName(Class<T> responseType, String name) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("name/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T getAnimalsByBirthdate(Class<T> responseType, String dateFrom, String dateTo) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("between/{0}/{1}", new Object[]{dateFrom, dateTo}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
