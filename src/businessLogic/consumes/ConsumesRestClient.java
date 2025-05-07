/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.consumes;

import DTO.AnimalGroupBean;
import DTO.ConsumesBean;
import DTO.ConsumesIdBean;
import DTO.ProductBean;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ui.utilities.ConfigReader;

/**
 * REST client for managing consume records via the Consumes RESTful service.
 * 
 * @author Pablo
 */
public class ConsumesRestClient implements IConsumesManager {

    private static final Logger LOGGER = Logger.getLogger("logger");

    private static final String BASE_URI = ConfigReader.getBaseUri();

    private Client client;

    private WebTarget webTarget;

    public ConsumesRestClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("consumes");
    }

    @Override
    public <T> T getConsumesByDateFrom(GenericType<T> responseType, String from) throws ClientErrorException {
        return webTarget.path("Desde/" + from)
                        .request(MediaType.APPLICATION_XML)
                        .get(responseType);
    }


    @Override
    public <T> T findConsumesByProduct(GenericType<T> responseType, String productId) throws ClientErrorException {
        return webTarget.path("Producto/" + productId)
                        .request(MediaType.APPLICATION_XML)
                        .get(responseType);
    }


    @Override
    public <T> T findConsumesByAnimalGroup(GenericType<T> responseType, String animalGroupName) throws ClientErrorException {
        return webTarget.path("AnimalGroup/" + animalGroupName)
                        .request(MediaType.APPLICATION_XML)
                        .get(responseType);
    }


    @Override
    public <T> T getConsumesByDateTo(GenericType<T> responseType, String to) throws ClientErrorException {
        return webTarget.path("Hasta/" + to)
                        .request(MediaType.APPLICATION_XML)
                        .get(responseType);
    }


   @Override
public Response deleteConsume(String productId, String animalGroupId) throws WebApplicationException {
    return webTarget.path("Delete")
        .queryParam("productId", productId) // Usa queryParam
        .queryParam("animalGroupId", animalGroupId) // Usa queryParam
        .request(MediaType.APPLICATION_XML)
        .delete();
}


    @Override
    public <T> T getAllConsumes(GenericType<T> responseType) throws ClientErrorException {
        return webTarget.path("All")
                        .request(MediaType.APPLICATION_XML)
                        .get(responseType);
    }


    @Override
    public <T> T getConsumesByDate(GenericType<T> responseType, String from, String to) throws ClientErrorException {
        return webTarget.path("Rango/" + from + "/" + to)
                        .request(MediaType.APPLICATION_XML)
                        .get(responseType);
    }

    @Override
    public void createConsume(Object requestEntity) throws ClientErrorException {
        webTarget.request(MediaType.APPLICATION_XML)
                 .post(javax.ws.rs.client.Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }


    @Override
    public void updateConsume(Object requestEntity) throws ClientErrorException {
        webTarget.request(MediaType.APPLICATION_XML)
                 .put(javax.ws.rs.client.Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }

    public void close() {
        client.close();
    }
}
