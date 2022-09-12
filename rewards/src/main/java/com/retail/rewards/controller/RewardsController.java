package com.retail.rewards.controller;

import com.retail.rewards.entity.Purchase;
import com.retail.rewards.exception.NoOrderFoundException;
import com.retail.rewards.model.PurchaseDto;
import com.retail.rewards.model.RewardsDto;
import com.retail.rewards.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RewardsController {

    @Autowired
    private RewardService rewardService;

    @PostMapping("/purchase")
    public ResponseEntity<Purchase> add(@Valid @RequestBody PurchaseDto purchaseDto){
        return new ResponseEntity<>(rewardService.addPurchase(purchaseDto), HttpStatus.OK);
    }

    @GetMapping("/rewards")
    public ResponseEntity<RewardsDto> get(@RequestParam("customerId") Long customerId) throws NoOrderFoundException {
        return new ResponseEntity<>(rewardService.getRewards(customerId), HttpStatus.OK);
    }

    @GetMapping("/rewards/all")
    public ResponseEntity<List<RewardsDto>> get() throws NoOrderFoundException {
        return new ResponseEntity<>(rewardService.getAllRewards(), HttpStatus.OK);
    }

}
