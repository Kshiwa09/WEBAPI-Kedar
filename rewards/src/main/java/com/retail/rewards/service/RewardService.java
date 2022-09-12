package com.retail.rewards.service;

import com.retail.rewards.entity.Purchase;
import com.retail.rewards.exception.NoOrderFoundException;
import com.retail.rewards.model.PurchaseDto;
import com.retail.rewards.model.RewardsDto;

import java.util.List;

public interface RewardService {

    Purchase addPurchase(PurchaseDto purchaseDto);

    RewardsDto getRewards(Long customerId) throws NoOrderFoundException;

    List<RewardsDto> getAllRewards() throws NoOrderFoundException;

}
