package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.DeliveryUpdateRequestDto;
import com.pmc.market.service.DeliveryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "Delivery Controller", tags = "배송 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @ApiOperation("배송시작")
    @PostMapping
    public ResponseEntity<?> startDelivery(@RequestBody long orderId) {
        deliveryService.insertDelivery(orderId);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("배송상태 변경")
    @PutMapping("/{deliveryId}")
    public ResponseEntity<?> updateDeliveryStatus(@PathVariable("deliveryId") long deliveryId,
                                                  @RequestBody DeliveryUpdateRequestDto deliveryRequest) {
        deliveryService.updateStatus(deliveryId, deliveryRequest);
        return ResponseEntity.ok(ResponseMessage.success());
    }
}
