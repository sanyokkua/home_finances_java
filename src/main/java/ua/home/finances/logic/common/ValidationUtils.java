package ua.home.finances.logic.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import ua.home.finances.logic.common.exceptions.ApplicationUserValidationException;
import ua.home.finances.logic.common.exceptions.ParamValidationException;
import ua.home.finances.logic.common.exceptions.PurchaseListValidationException;
import ua.home.finances.logic.common.exceptions.PurchaseValidationException;
import ua.home.finances.logic.db.models.ApplicationUser;
import ua.home.finances.logic.db.models.Purchase;
import ua.home.finances.logic.db.models.PurchaseList;

import java.util.Objects;
import java.util.function.BooleanSupplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUtils {

    public static void validateApplicationUser(ApplicationUser applicationUser) {
        if (Objects.isNull(applicationUser)) {
            throw new ApplicationUserValidationException("ApplicationUser is null");
        }
        if (StringUtils.isBlank(applicationUser.getPassword())) {
            throw new ApplicationUserValidationException("ApplicationUser PASSWORD is blank");
        }
        validateEmail(applicationUser.getEmail());
        if (StringUtils.isBlank(applicationUser.getNickname())) {
            throw new ApplicationUserValidationException("ApplicationUser NICKNAME is blank");
        }
        if (Objects.isNull(applicationUser.getRole())) {
            throw new ApplicationUserValidationException("ApplicationUser ROLE is null");
        }
    }

    public static void validateEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new ParamValidationException("EMAIL is blank");
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new ParamValidationException("EMAIL is not valid");
        }
    }

    public static void validatePurchase(Purchase purchase) {
        if (Objects.isNull(purchase)) {
            throw new PurchaseValidationException("Purchase is null");
        }
        if (Objects.isNull(purchase.getPurchaseListId()) || purchase.getPurchaseListId() < 0) {
            throw new PurchaseValidationException("Purchase LIST_ID should be valid positive LONG");
        }
        if (StringUtils.isBlank(purchase.getName())) {
            throw new PurchaseValidationException("Purchase NAME is blank");
        }
        if (Objects.isNull(purchase.getCurrency())) {
            throw new PurchaseValidationException("Purchase CURRENCY is null");
        }
        if (Objects.isNull(purchase.getDate())) {
            throw new PurchaseValidationException("Purchase DATE is null");
        }
        if (purchase.getCoins() < 0) {
            throw new PurchaseValidationException("Purchase COINS should be >= 0");
        }
        if (StringUtils.isBlank(purchase.getCategory())) {
            throw new PurchaseValidationException("Purchase CATEGORY is blank");
        }
    }

    public static void validatePurchaseList(PurchaseList purchaseList) {
        if (Objects.isNull(purchaseList)) {
            throw new PurchaseListValidationException("PurchaseList is null");
        }
        if (Objects.isNull(purchaseList.getListUserId()) || purchaseList.getListUserId() < 0) {
            throw new PurchaseListValidationException("PurchaseList USER ID should be valid positive LONG");
        }
        if (StringUtils.isBlank(purchaseList.getName())) {
            throw new PurchaseListValidationException("PurchaseList NAME is blank");
        }
    }

    public static <T> void validateParamCommon(BooleanSupplier predicate, String errorMessage) {
        if (predicate.getAsBoolean()) {
            throw new ParamValidationException(errorMessage);
        }
    }

}
