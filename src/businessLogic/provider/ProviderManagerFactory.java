/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.provider;

/**
 *
 * @author InigoFreire
 */
public class ProviderManagerFactory {
 
    private static IProviderManager providerManager;
    
    public static IProviderManager get(){
        if(providerManager==null){
           providerManager=new ProviderRESTClient();
        }
        return providerManager;
    }      
    
}
