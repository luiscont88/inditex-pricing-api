package com.inditex.store.infrastructure.rest.unit;

import com.inditex.store.application.services.PriceService;
import com.inditex.store.domain.model.Price;
import com.inditex.store.infrastructure.rest.PriceController;
import com.inditex.store.infrastructure.rest.dto.PriceResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PriceControllerUnitTest {

    private PriceService priceService;
    private PriceController priceController;

    @BeforeEach
    void setUp() {
        priceService = mock(PriceService.class);
        priceController = new PriceController(priceService);
    }

    @Test
    void whenValidRequest_thenReturnsPriceDTO() {
        // Arrange
        LocalDateTime requestDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price price = Price.builder()
                .id(1L)
                .brandId(brandId)
                .productId(productId)
                .priceList(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .priority(0)
                .price(new BigDecimal("35.50"))
                .curr("EUR")
                .build();

        when(priceService.findApplicablePrice(requestDate, productId, brandId))
                .thenReturn(Optional.of(price));

        // Act
        ResponseEntity<PriceResponseDTO> response = priceController.getPrice(requestDate, productId, brandId);

        // Assert
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().price()).isEqualByComparingTo("35.50");
        assertThat(response.getBody().priceList()).isEqualTo(1);
        verify(priceService, times(1)).findApplicablePrice(requestDate, productId, brandId);
    }

    @Test
    void whenNoPriceFound_thenReturns404() {
        // Arrange
        LocalDateTime requestDate = LocalDateTime.of(2022, 1, 1, 0, 0);
        Long productId = 99999L;
        Long brandId = 2L;

        when(priceService.findApplicablePrice(requestDate, productId, brandId))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<PriceResponseDTO> response = priceController.getPrice(requestDate, productId, brandId);

        // Assert
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
        assertThat(response.getStatusCode().value()).isEqualTo(404);
        verify(priceService, times(1)).findApplicablePrice(requestDate, productId, brandId);
    }
}
