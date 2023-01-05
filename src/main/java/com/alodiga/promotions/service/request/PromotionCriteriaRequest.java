
package com.alodiga.promotions.service.request;

import java.io.Serializable;

public class PromotionCriteriaRequest implements Serializable {

    private static final long serialVersionUID = 1L;
      
    private String promotionType;
      
    private String transactionType;

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}


