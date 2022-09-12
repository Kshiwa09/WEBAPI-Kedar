package com.retail.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardsDto {

    private Long customerId;
    private Map<String, Double> rewards;
    private Double total;

}
