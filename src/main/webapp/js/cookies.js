/**
 * The bikeStore.Cookie namespace handles all aspects of the application related to
 * cookie functionality. It provides functions to restore cookie object in
 * different formats and get cookie objects in corresponding types.
 */
var bikeStore = bikeStore || {};

(function(cookie)  {

  /**
   * Setter for MaskedWallet JWT.
   * It restores MaskedWallet JWT to persist it for page refreshes.
   * @param {Object} maskedWallet The MaskedWalletResponse.
   */
  cookie.setMaskedWallet = function(maskedWallet) {
    // Store MakedWallet in JSON format.
    localStorage.setItem('maskedWallet', JSON.stringify(maskedWallet));
  };

  /**
   * Setter for FullWallet JWT.
   * It restores FullWallet JWT to persist it for page refreshes.
   * @param {String} fullWallet The FullWalletResponse.
   */
  cookie.setFullWallet = function(fullWallet) {
    // Store FullWallet in JSON format.
    localStorage.setItem('fullWallet', JSON.stringify(fullWallet));
  };

  /**
   * Setter for Changed MaskedWallet JWT
   * It restores Changed MaskWallet JWT to persist it for page refreshes.
   * @param {String} changedJWT ChangedMaskWallet JWT with
   * Google Transaction Id.
   */
  cookie.setChangeJwt = function(changedJWT) {
    // Store changedJWT in JSON format.
    localStorage.setItem('changedJwt', JSON.stringify(changedJWT));
  };

  /**
   * Setter for current item.
   * It restores current item object to persist currently selected item object
   * for page refreshes.
   * @param {Object} item CurrentItem model which is used to represent
   * currently selected item.
   */
  cookie.setCurrentItem = function(item) {
    // Store current item in JSON format.
    localStorage.setItem('currentItem', JSON.stringify(item));
  };

  /**
   * Setter for cart items.
   * It restores current cart items object to persist currently selected items 
   * object for page refreshes.
   * @param {Object} cartItem Cart model which is used to represent
   * currently selected cart items.
   */
  cookie.setCartItem = function(cartItem) {
    // Store cart items.
    localStorage.setItem('cartItem', JSON.stringify(cartItem));
  };

  /**
   * Setter for transaction ID
   * It restores transaction ID to persist it for page refreshes.
   * @param {String} transactionId ID which ties various api requests
   * for a single order.
   */
  cookie.setTransactionId = function(transactionId) {
    localStorage.setItem('transactionId', transactionId);
  };

  /**
   * Setter for email.
   * It restores user's email to persist it for page refreshes.
   * @param {String} email Id of user.
   */
  cookie.setEmail = function(email) {
    $.cookie('email', email);
  };

  /**
   * Setter for OAuth2 access_token.
   * It restores accessToken to allow users to persist their Wallet
   * pre-authorization.
   * @param {Object} accessToken Acquired OAuth accessToken.
   * @param {String} expirationMinutes Expiration time for the accessToken
   * in minutes from now.
   */
  cookie.setAccessToken = function(accessToken, expirationMinutes) {
    // Sync the expiration time between the cookie object and the accessToken.
    var expiryDate = new Date();
    expiryDate.setTime(expiryDate.getTime() + (expirationMinutes * 60 * 1000));
    $.cookie('accessToken', accessToken, {expires: expiryDate});
  };

  /**
   * Getter for OAuth2 access_token.
   * @return {Object} This returns the accessToken of the user's OAuth
   * authorization.
   */
  cookie.getAccessToken = function() {
    return $.cookie('accessToken');
  };

  /**
   * Getter for MaskedWallet JWT.
   * @return {Object} This returns the MaskedWallet JWT.
   */
  cookie.getMaskedWallet = function() {
    // Convert the obtained cookie into JSON object and return it.
    return localStorage.getItem('maskedWallet') ? JSON.parse(localStorage.getItem('maskedWallet')) :
      null;
  }

  /**
   * Getter for FullWallet JWT.
   * @return {Object} This returns the FullWallet JWT.
   */
  cookie.getFullWallet = function() {
    // Convert the obtained cookie into JSON object and return it.
    return localStorage.getItem('fullWallet') ? JSON.parse(localStorage.getItem('fullWallet')) : null;
  };

  /**
   * Getter for changed MaskedWallet JWT.
   * @return {Object} This returns the Changed MaskWallet JWT.
   */
  cookie.getChangeJwt = function() {
    // Convert the obtained cookie into JSON object and return it.
    return localStorage.getItem('changedJwt') ? JSON.parse(localStorage.getItem('changedJwt')) : null;
  };

  /**
   * Getter for current item object.
   * @return {Object} This returns currently selected item as a
   * CurrentItem model.
   */
  cookie.getCurrentItem = function() {
    // Convert the obtained cookie from JSON format into CurrentItem
    // model and return it.
    return new Backbone.Model(JSON.parse(localStorage.getItem('currentItem')));
  };

  /**
   * Getter for user's email.
   * @return {String} This returns user's email.
   */
  cookie.getEmail = function() {
    return $.cookie('email');
  };

  /**
   * Getter for cart items.
   * @return {Object} This returns cart items as a Cart model.
   */
  cookie.getCartItem = function() {
    return new Backbone.Collection(JSON.parse(localStorage.getItem('cartItem')));
  };
  cookie.updateCartItem = function(modelId, cartItem) {
    cartItem.remove(cartItem.at(modelId));
    // Update cart items.
    localStorage.getItem('cartItem', JSON.stringify(cartItem));
    bikeStore.Wallet.createButton();
  };

  /**
   * Getter for transaction ID.
   * @return {String} This returns the Transaction ID.
   */
  cookie.getTransactionId = function() {
    return localStorage.getItem('transactionId');
  };
})(window.bikeStore.Cookie = window.bikeStore.Cookie || {});
