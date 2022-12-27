package ua.home.finances.finances.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.finances.common.Currency;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
    private Long purchaseId;
    private String name;
    private long coins;
    private Currency currency;
    private LocalDate date;
    private String category;
}
