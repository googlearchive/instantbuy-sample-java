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
 * A simple class to represent the Pay request bean.
 */
public class Pay {

  private String estimatedTotalPrice;
  private String currencyCode;

  public Pay() {
    // Empty constructor used in Gson conversion of JSON -> Java Objects
  }

  // Constructor using builder
  private Pay(Builder builder) {
    this.estimatedTotalPrice = builder.estimatedTotalPrice;
    this.currencyCode = builder.currencyCode;
  }

  public String getEstimatedTotalPrice() {
    return estimatedTotalPrice;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  /**
   * Creates a new builder.
   * @return A new builder to create the Pay object.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Helper class for creating a Pay object.
   */
  public static class Builder {
    private String estimatedTotalPrice;
    private String currencyCode;

    private Builder() {
    }

    public Builder setEstimatedTotalPrice(String estimatedTotalPrice) {
      this.estimatedTotalPrice = estimatedTotalPrice;
      return this;
    }

    public Builder setCurrencyCode(String currencyCode) {
      this.currencyCode = currencyCode;
      return this;
    }

    public Pay build() {
      // validate and return
      return new Pay(this);
    }
  }
}
