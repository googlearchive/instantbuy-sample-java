/**
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.wallet.online.jwt;

/**
 * A Simple class representing the FullWalletRequest object.
 */
public class FullWalletRequest {
  private String googleTransactionId;
  private String clientId;
  private String merchantName;
  private String origin;

  private Cart cart;


  public FullWalletRequest() {
    // Empty constructor used in Gson conversion of JSON -> Java Objects
  }

  private FullWalletRequest(Builder builder) {
    this.googleTransactionId = builder.googleTransactionId;
    this.clientId = builder.clientId;
    this.merchantName = builder.merchantName;
    this.origin = builder.origin;
    this.cart = builder.cart;
  }

  public String getGoogleTransactionId() {
    return googleTransactionId;
  }

  public String getClientId() {
    return clientId;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public String getOrigin() {
    return origin;
  }

  public Cart getCart() {
    return cart;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Helper class to create a FullWalletRequest.
   */
  public static class Builder {
    private String googleTransactionId;
    private String clientId;
    private String merchantName;
    private String origin;
    private Cart cart;

    private Builder() {}

    public Builder setGoogleTransactionId(String googleTransactionId) {
      this.googleTransactionId = googleTransactionId;
      return this;
    }

    public Builder setClientId(String clientId) {
      this.clientId = clientId;
      return this;
    }

    public Builder setMerchantName(String merchantName) {
      this.merchantName = merchantName;
      return this;
    }

    public Builder setOrigin(String origin) {
      this.origin = origin;
      return this;
    }

    public Builder setCart(Cart cart) {
      this.cart = cart;
      return this;
    }

    public FullWalletRequest build() {
      // validate and return
      return new FullWalletRequest(this);
    }
  }
}
