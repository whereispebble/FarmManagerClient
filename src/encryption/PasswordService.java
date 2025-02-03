/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author Aitziber
 */
public class PasswordService {
    
    public static String resetPassword(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        return RandomStringUtils.random( 10, characters );
    }
    public static String recoverPassword(){
        String pw = null;
      // ??
        return pw;
    }
}
