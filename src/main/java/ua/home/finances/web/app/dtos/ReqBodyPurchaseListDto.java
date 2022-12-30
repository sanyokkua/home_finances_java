package ua.home.finances.web.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.logic.services.dtos.PurchaseListDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqBodyPurchaseListDto {
    private String purchaseListName;

    public static PurchaseListDto fromRequest(ReqBodyPurchaseListDto reqBodyPurchaseListDto) {
        return PurchaseListDto.builder().name(reqBodyPurchaseListDto.getPurchaseListName()).build();
    }
}
