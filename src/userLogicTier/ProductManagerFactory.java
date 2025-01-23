/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import model.ProductBean;

/**
 *
 * @author InigoFreire
 */
public class ProductManagerFactory {
 
    public static ProductRESTClient get(){
        return new ProductRESTClient();
    }
    
}
