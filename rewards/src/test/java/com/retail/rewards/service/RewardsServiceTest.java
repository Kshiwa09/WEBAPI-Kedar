package com.retail.rewards.service;

import com.retail.rewards.entity.Purchase;
import com.retail.rewards.exception.NoOrderFoundException;
import com.retail.rewards.model.RewardsDto;
import com.retail.rewards.repository.PurchaseRepository;
import com.retail.rewards.service.impl.RewardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class RewardsServiceTest {

    @InjectMocks
    private RewardService rewardService = new RewardServiceImpl();

    @Mock
    private PurchaseRepository purchaseRepository;

    @Test
    public void testRewardsPoint() throws NoOrderFoundException {
        Mockito.when(purchaseRepository.findByCustomerId(any())).thenReturn(mock());
        RewardsDto allRewards = rewardService.getRewards(1L);
        Assertions.assertEquals(200, allRewards.getTotal());
    }

    @Test
    public void testRewardsPointWithNoValuesMoreThan50() throws NoOrderFoundException {
        Mockito.when(purchaseRepository.findByCustomerId(any())).thenReturn(mockLessThan50());
        RewardsDto allRewards = rewardService.getRewards(1L);
        Assertions.assertEquals(0, allRewards.getTotal());
    }

    public List<Purchase> mock() {
        return Stream.of(140.0, 30.0, 90.0, 70.0, 10.0, 40.0, 60.0)
                .map(amount -> Purchase.builder()
                        .amount(amount)
                        .purchaseDate(new Date())
                        .customerId(1L)
                        .productName("iPhone 14")
                        .build()).collect(Collectors.toList());
    }

    public List<Purchase> mockLessThan50() {
        return Stream.of(30.0,40.0,20.0,30.0)
                .map(amount -> Purchase.builder()
                        .amount(amount)
                        .purchaseDate(new Date())
                        .customerId(1L)
                        .productName("iPhone 14")
                        .build()).collect(Collectors.toList());
    }

}
