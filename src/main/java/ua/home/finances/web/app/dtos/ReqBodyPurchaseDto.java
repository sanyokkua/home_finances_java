package ua.home.finances.web.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.logic.common.Currency;
import ua.home.finances.logic.db.models.Purchase;
import ua.home.finances.logic.services.dtos.PurchaseDto;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqBodyPurchaseDto {
    private Long purchaseId;
    private String purchaseName;
    private Long purchaseCoins;
    private String purchaseCurrency;
    private String purchaseDate;
    private String purchaseCategory;

    public static PurchaseDto fromRequest(ReqBodyPurchaseDto reqBodyPurchaseDto) {
        return PurchaseDto.builder()
                .purchaseId(reqBodyPurchaseDto.getPurchaseId())
                .name(reqBodyPurchaseDto.getPurchaseName())
                .coins(reqBodyPurchaseDto.getPurchaseCoins())
                .currency(Currency.valueOf(reqBodyPurchaseDto.getPurchaseCurrency()))
                .date(LocalDate.parse(reqBodyPurchaseDto.getPurchaseDate(), Purchase.DATE_FORMATTER))
                .category(reqBodyPurchaseDto.getPurchaseCategory())
                .build();
    }
}
