package Common;

/**
 * Enum made to define different commands
 */
public enum Command{
    // Offer commands
    ADD_OFFER,
    REMOVE_OFFER,
    GET_OFFERS,

    // Asset commands
    ADD_ASSET,
    REMOVE_ASSET,
    GET_ASSET,

    // OUAsset commands
    ADD_OU_ASSET,
    DELETE_OU_ASSET,
    GET_OU_ASSET,

    // OU commands
    ADD_OU,
    EDIT_OU,
    REMOVE_OU,

    // Login commands
    LOGIN,
    CHANGE_PASSWORD,

    // Boolean commands
    FAIL,
    SUCCESS
}