package com.retail.rewards.service.impl;

import com.retail.rewards.entity.Purchase;
import com.retail.rewards.exception.NoOrderFoundException;
import com.retail.rewards.model.PurchaseDto;
import com.retail.rewards.model.RewardsDto;
import com.retail.rewards.repository.PurchaseRepository;
import com.retail.rewards.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public Purchase addPurchase(PurchaseDto purchaseDto) {
        return purchaseRepository.save(purchaseDto.convert());
    }

    @Override
    public RewardsDto getRewards(Long customerId) throws NoOrderFoundException {
        List<Purchase> purchases = purchaseRepository.findByCustomerId(customerId);
        if(purchases.isEmpty()){
            throw new NoOrderFoundException("No orders present for the customer ID "+customerId);
        }
        Map<String, Double> rewards = getRewardsMap(purchases);

        return RewardsDto.builder()
                .customerId(customerId)
                .total(rewards.values().stream().reduce(0.0,Double::sum))
                .rewards(rewards)
                .build();
    }

    private Map<String, Double> getRewardsMap(List<Purchase> purchases) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
        return purchases.stream()
                .collect(Collectors.toMap(purchase -> sdf.format(purchase.getPurchaseDate()), this::getRewardPerOrder,Double::sum));
    }

    @Override
    public List<RewardsDto> getAllRewards() throws NoOrderFoundException {
        List<Purchase> purchases = purchaseRepository.findAll();
        if(purchases.isEmpty()){
            throw new NoOrderFoundException("No orders present");
        }
        Map<Long, List<Purchase>> userBasedPurchases = purchases.stream().collect(Collectors.groupingBy(Purchase::getCustomerId));

        return userBasedPurchases.entrySet().stream().map(entry->{
            Map<String, Double> rewardsMap = getRewardsMap(entry.getValue());
            return RewardsDto.builder()
                    .customerId(entry.getKey())
                    .total(rewardsMap.values().stream().reduce(0.0,Double::sum))
                    .rewards(rewardsMap)
                    .build();
        }).collect(Collectors.toList());
    }

    private Double getRewardPerOrder(Purchase purchase) {
        Double amount = purchase.getAmount();
        double rewards=0.0;
        if(amount>50){
            double difference = amount-50;
            rewards+=difference*1;
            if(difference>50) {
                rewards += (difference - 50) * 1;
            }
        }
        return rewards;
    }

    @PostConstruct
    public void mock() {
        List<Purchase> purchases = Stream.of(140.0, 30.0, 90.0, 70.0, 10.0, 40.0,60.0)
                .map(amount -> Purchase.builder()
                .amount(amount)
                .purchaseDate(getRandomDate())
                .customerId(1L)
                .productName("iPhone 14")
                .build()).collect(Collectors.toList());
        purchaseRepository.saveAll(purchases);
    }

    private Date getRandomDate(){
        Calendar calendar = GregorianCalendar.getInstance();
        Random random = new Random();
        int result = random.nextInt(89);
        calendar.add(Calendar.DATE, -result);
        return calendar.getTime();
    }
}
