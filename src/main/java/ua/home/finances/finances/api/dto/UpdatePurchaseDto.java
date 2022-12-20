package ua.home.finances.finances.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.finances.common.Currency;
import ua.home.finances.finances.db.models.Purchase;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePurchaseDto {
    private Long id;
    private String name;
    private long coins;
    private String currency;
    private String date;

    public static Purchase map(UpdatePurchaseDto updatePurchaseDto) {
        return Purchase.builder()
                       .id(updatePurchaseDto.id)
                       .name(updatePurchaseDto.name)
                       .coins(updatePurchaseDto.coins)
                       .currency(Currency.valueOf(updatePurchaseDto.currency))
                       .date(LocalDate.parse(updatePurchaseDto.date))
                       .build();
    }
}
