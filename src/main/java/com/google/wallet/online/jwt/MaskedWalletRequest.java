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
 * A simple class representing a Masked Wallet Request.
 */
public class MaskedWalletRequest {
  private String googleTransactionId;
  private String merchantTransactionId;
  private String clientId;
  private String merchantName;
  private String origin;

  private String email;
  private Boolean phoneNumberRequired;

  private Pay pay;
  private Ship ship;
  
  private Boolean useMinimalAddresses;

  public MaskedWalletRequest() {
  }

  // Constructor using builder
  private MaskedWalletRequest(Builder builder) {
    this.googleTransactionId = builder.googleTransactionId;
    this.merchantTransactionId = builder.merchantTransactionId;
    this.clientId = builder.clientId;
    this.merchantName = builder.merchantName;
    this.origin = builder.origin;
    this.email = builder.email;
    this.phoneNumberRequired = builder.phoneNumberRequired;
    this.pay = builder.pay;
    this.ship = builder.ship;
    this.useMinimalAddresses = builder.useMinimalAddresses;
  }
  
  public String getGoogleTransactionId() {
    return googleTransactionId;
  }

  public String getMerchantTransactionId() {
    return merchantTransactionId;
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

  public String getEmail() {
    return email;
  }

  public Boolean getPhoneNumberRequired() {
    return phoneNumberRequired;
  }

  public Pay getPay() {
    return pay;
  }

  public Ship getShip() {
    return ship;
  }

  public Boolean getUseMinimalAddresses() {
    return useMinimalAddresses;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Convert back to a Builder instance so the current instance can be mutated.
   * @return Builder instance represented by the current MaskedWalletRequest.
   */
  public Builder toBuilder() {
    return newBuilder()
        .setGoogleTransactionId(googleTransactionId)
        .setClientId(clientId)
        .setEmail(email)
        .setMerchantName(merchantName)
        .setOrigin(origin)
        .setMerchantTransactionId(merchantTransactionId)
        .setPay(pay)
        .setShip(ship)
        .setPhoneNumberRequired(phoneNumberRequired)
        .setUseMinimalAddresses(useMinimalAddresses);
  }

  /**
   * Helper class to generate the MaskedWalletRequest.
   */
  public static class Builder {
    private String googleTransactionId;
    private String merchantTransactionId;
    private String clientId;
    private String merchantName;
    private String origin;
    private String email;
    private Boolean phoneNumberRequired;
    private Pay pay;
    private Ship ship;
    private boolean useMinimalAddresses;

    private Builder() {
    }

    public static Builder newBuilder() {
      return new Builder();
    }


    public Builder setGoogleTransactionId(String googleTransactionId) {
      this.googleTransactionId = googleTransactionId;
      return this;
    }

    public Builder setMerchantTransactionId(String merchantTransactionId) {
      this.merchantTransactionId = merchantTransactionId;
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

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setPhoneNumberRequired(Boolean phoneNumberRequired) {
      this.phoneNumberRequired = phoneNumberRequired;
      return this;
    }

    public Builder setPay(Pay pay) {
      this.pay = pay;
      return this;
    }

    public Builder setShip(Ship ship) {
      this.ship = ship;
      return this;
    }
    
    public Builder setUseMinimalAddresses(boolean useMinimalAddresses) {
      this.useMinimalAddresses = useMinimalAddresses;
      return this;
    }

    public MaskedWalletRequest build() {
      // validate and return
      // issuer, ship
      return new MaskedWalletRequest(this);
    }
  }
}
