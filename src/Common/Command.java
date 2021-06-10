package Common;

/**
 * Enum made to define different commands
 */
public enum Command{
    // Offer commands
    ADD_OFFER,
    REMOVE_OFFER,
    GET_OFFERS,
    EDIT_OFFER,

    // Asset commands
    ADD_ASSET,
    REMOVE_ASSET,
    GET_ASSET,
    GET_ASSET_SIZE,

    // OUAsset commands
    ADD_OU_ASSET,
    DELETE_OU_ASSET,
    GET_OU_ASSET,
    EDIT_OU_ASSET,

    // OU commands
    ADD_OU,
    GET_OU,
    EDIT_OU_CREDIT,
    REMOVE_OU,

    // User commands
    ADD_USER,
    GET_USER,
    REMOVE_USER,
    GET_SINGLE_USER,
    PASSWORD_CHECK,

    // Login commands
    LOGIN,
    CHANGE_PASSWORD,

    // Boolean commands
    FAIL,
    SUCCESS,
}