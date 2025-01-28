/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import DTO.ProductBean;

/**
 *
 * @author InigoFreire
 */
public class ProviderManagerFactory {
 
    public static ProviderRESTClient get(){
        return new ProviderRESTClient();
    }
    
}
