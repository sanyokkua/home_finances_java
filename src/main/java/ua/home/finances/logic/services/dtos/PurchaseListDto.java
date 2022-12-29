package ua.home.finances.logic.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.logic.db.models.PurchaseList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseListDto {
    private Long userId;
    private Long listId;
    private String name;

    public static PurchaseListDto fromModel(PurchaseList purchaseList) {
        return PurchaseListDto.builder()
                .listId(purchaseList.getListId())
                .userId(purchaseList.getListUserId())
                .name(purchaseList.getName())
                .build();
    }

    public static PurchaseList fromDto(PurchaseListDto purchaseListDto) {
        return PurchaseList.builder()
                .listId(purchaseListDto.getListId())
                .listUserId(purchaseListDto.getUserId())
                .name(purchaseListDto.getName())
                .build();
    }
}
