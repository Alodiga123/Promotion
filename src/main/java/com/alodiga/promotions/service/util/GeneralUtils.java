/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alodiga.promotions.service.util;


import com.alodiga.promotions.domain.enumeration.PromotionType;
import com.alodiga.promotions.domain.enumeration.TipoTransaction;



public class GeneralUtils {
   
    
    public static PromotionType getPromotionType(String promotionType) {
        switch (promotionType) {
            case Constants.REFERRED:
                return PromotionType.REFERRED;
            case  Constants.BIRTHDAY:
                return PromotionType.BIRTHDAY;
            case  Constants.FREQUENT:
                return PromotionType.FREQUENT;
            case  Constants.BY_PRODUCT:
                return PromotionType.BY_PRODUCT;
            case Constants.REGISTRATION:
                return PromotionType.REGISTRATION;           
            default:
                break;
        }
        return null;
    }
    
    public static TipoTransaction getTransactionType(String transactionType) {
        switch (transactionType) {
            case Constants.RETIRO_MANUAL:
                return TipoTransaction.RETIRO_MANUAL;
            case Constants.RECARGA_MANUAL:
                return TipoTransaction.RECARGA_MANUAL;
            case Constants.PAGO_A_COMERCIO_QR:
                return TipoTransaction.PAGO_A_COMERCIO_QR;
            case Constants.TRANSFERENCIA:
                return TipoTransaction.TRANSFERENCIA;
            case Constants.RETIRO_POR_PAGO_MOVIL:
                return TipoTransaction.PAGO_A_COMERCIO_QR;
            default:
                break;
        }
        return null;
    }




        
        
        
        
}
