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
public class RespPurchaseListDto {
    private long purchaseListId;
    private String purchaseListName;

    public static RespPurchaseListDto fromPurchaseListDto(PurchaseListDto purchaseListDto) {
        return RespPurchaseListDto.builder()
                .purchaseListId(purchaseListDto.getListId())
                .purchaseListName(purchaseListDto.getName())
                .build();
    }
}
