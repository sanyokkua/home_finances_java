package ua.home.finances.finances.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import ua.home.finances.finances.common.exceptions.AppUserValidationException;
import ua.home.finances.finances.common.exceptions.ParamValidationException;
import ua.home.finances.finances.common.exceptions.PurchaseValidationException;
import ua.home.finances.finances.common.exceptions.UserAuthInfoValidationException;
import ua.home.finances.finances.db.models.AppUser;
import ua.home.finances.finances.db.models.Purchase;
import ua.home.finances.finances.db.models.PurchaseList;
import ua.home.finances.finances.db.models.UserAuthInfo;

import java.util.Objects;
import java.util.function.BooleanSupplier;

public final class ValidationUtils {

    public static void validateUserAuth(UserAuthInfo userAuthInfo) {
        if (Objects.isNull(userAuthInfo)) {
            throw new UserAuthInfoValidationException("UserAuthInfo is null");
        }
        if (StringUtils.isBlank(userAuthInfo.getPassword())) {
            throw new UserAuthInfoValidationException("UserAuthInfo PASSWORD is blank");
        }
        validateEmail(userAuthInfo.getEmail());
    }

    public static void validateEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new UserAuthInfoValidationException("EMAIL is blank");
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new UserAuthInfoValidationException("EMAIL is not valid");
        }
    }

    public static void validateAppUser(AppUser appUser) {
        if (Objects.isNull(appUser)) {
            throw new AppUserValidationException("AppUser is null");
        }
        if (Objects.isNull(appUser.getUserId()) || appUser.getUserId() < 0) {
            throw new AppUserValidationException("AppUser ID should be valid positive LONG");
        }
        if (StringUtils.isBlank(appUser.getNickname())) {
            throw new AppUserValidationException("AppUser NICKNAME is blank");
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
    }

    public static void validatePurchaseList(PurchaseList purchaseList) {
        if (Objects.isNull(purchaseList)) {
            throw new PurchaseValidationException("PurchaseList is null");
        }
        if (Objects.isNull(purchaseList.getListUserId()) || purchaseList.getListUserId() < 0) {
            throw new PurchaseValidationException("PurchaseList USER ID should be valid positive LONG");
        }
        if (StringUtils.isBlank(purchaseList.getName())) {
            throw new PurchaseValidationException("PurchaseList NAME is blank");
        }
    }

    public static <T> void validateParamCommon(BooleanSupplier predicate, String errorMessage) {
        if (predicate.getAsBoolean()) {
            throw new ParamValidationException(errorMessage);
        }
    }

}
