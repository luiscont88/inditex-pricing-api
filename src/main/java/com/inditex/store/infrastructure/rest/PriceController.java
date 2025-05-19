package com.inditex.store.infrastructure.rest;

import com.inditex.store.application.services.PriceService;
import com.inditex.store.infrastructure.rest.dto.PriceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@Validated
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Operation(summary = "Get applicable price for a product, brand and date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price found"),
            @ApiResponse(responseCode = "404", description = "No applicable price found")
    })
    @GetMapping("/price")
    public ResponseEntity<PriceResponseDTO> getPrice(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam @NotNull Long productId,
            @RequestParam @NotNull Long brandId
    ) {
        return priceService.findApplicablePrice(date, productId, brandId)
                .map(price -> ResponseEntity.ok(new PriceResponseDTO(
                        price.getProductId(),
                        price.getBrandId(),
                        price.getPriceList(),
                        price.getStartDate(),
                        price.getEndDate(),
                        price.getPrice(),
                        price.getCurr()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

}
