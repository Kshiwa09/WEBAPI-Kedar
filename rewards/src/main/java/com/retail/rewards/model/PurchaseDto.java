package com.retail.rewards.model;

import com.retail.rewards.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    @NotBlank(message = "Product name can't be blank")
    private String productName;

    @NotNull(message = "Product amount cant be null")
    @Min(value = 1, message = "Amount cant be less than 1")
    private Double amount;

    public Purchase convert(){
        return Purchase.builder()
                .productName(productName)
                .amount(amount)
                .customerId(customerId)
                .purchaseDate(new Date())
                .build();
    }
}
