package businessLogic.consumes;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 * Interface for managing consume operations.
 * Defines the methods to interact with consume data, including fetching, creating, updating, 
 * and deleting consumes in the system.
 * 
 * @author Pablo
 */
public interface IConsumesManager {
  
  public <T> T getConsumesByDateFrom(GenericType<T> responseType, String from) throws WebApplicationException;

  public <T> T findConsumesByProduct(GenericType<T> responseType, String ProductId) throws WebApplicationException;

  public Response deleteConsume(String productId, String animalGroupId) throws WebApplicationException;

  public <T> T findConsumesByAnimalGroup(GenericType<T> responseType, String nameAnimalGroup) throws WebApplicationException;

  public <T> T getConsumesByDateTo(GenericType<T> responseType, String to) throws WebApplicationException;

  public <T> T getAllConsumes(GenericType<T> responseType) throws WebApplicationException;

  public <T> T getConsumesByDate(GenericType<T> responseType, String from, String to) throws WebApplicationException;

  public void createConsume(Object requestEntity) throws WebApplicationException;

  public void updateConsume(Object requestEntity) throws WebApplicationException;
}


