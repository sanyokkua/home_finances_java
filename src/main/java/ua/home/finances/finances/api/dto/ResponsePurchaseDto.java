package ua.home.finances.finances.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.finances.common.Currency;
import ua.home.finances.finances.db.models.Purchase;
import ua.home.finances.finances.services.dtos.PurchaseDto;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePurchaseDto {
    private Long purchaseId;
    private String name;
    private long coins;
    private String currency;
    private String date;
    private String category;

    public static ResponsePurchaseDto from(PurchaseDto purchaseDto) {
        return ResponsePurchaseDto.builder()
                .purchaseId(purchaseDto.getPurchaseId())
                .name(purchaseDto.getName())
                .coins(purchaseDto.getCoins())
                .currency(purchaseDto.getCurrency().name())
                .date(purchaseDto.getDate().format(Purchase.DATE_FORMATTER))
                .category(purchaseDto.getCategory())
                .build();
    }

    public static PurchaseDto from(ResponsePurchaseDto purchaseDto) {
        return PurchaseDto.builder()
                .purchaseId(purchaseDto.getPurchaseId())
                .name(purchaseDto.getName())
                .coins(purchaseDto.getCoins())
                .currency(Currency.valueOf(purchaseDto.getCurrency()))
                .date(LocalDate.parse(purchaseDto.date, Purchase.DATE_FORMATTER))
                .category(purchaseDto.getCategory())
                .build();
    }
}
