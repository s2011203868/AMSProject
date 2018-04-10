package com.purple.ams.ssm.util;

/**
 *  
 * @ClassName: EncryUtil 
 * @Description: DES加密解密工具类 
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:53:44
 */
public class EncryUtil {  
      
    /** 
     * 使用默认密钥进行DES加密 
     */  
    public static String encrypt(String plainText) {  
        try {  
            return new DES().encrypt(plainText);  
        } catch (Exception e) {  
            return null;  
        }  
    }  
  
      
    /** 
     * 使用指定密钥进行DES解密 
     */  
    public static String encrypt(String plainText, String key) {  
        try {  
            return new DES(key).encrypt(plainText);  
        } catch (Exception e) {  
            return null;  
        }  
    }  
      
  
    /** 
     * 使用默认密钥进行DES解密 
     */  
    public static String decrypt(String plainText) {  
        try {  
            return new DES().decrypt(plainText);  
        } catch (Exception e) {  
            return null;  
        }  
    }  
  
      
    /** 
     * 使用指定密钥进行DES解密 
     */  
    public static String decrypt(String plainText, String key) {  
        try {  
            return new DES(key).decrypt(plainText);  
        } catch (Exception e) {  
            return null;  
        }  
    }  
  
}  
