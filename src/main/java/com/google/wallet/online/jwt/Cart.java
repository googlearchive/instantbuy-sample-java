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

import java.util.ArrayList;
import java.util.List;

/**
 * Bean to represent the cart of items purchased.
 */
public class Cart {

  private String totalPrice;
  private String currencyCode;
  private List<LineItem> lineItems;

  public Cart() {
    // Empty constructor used in Gson conversion of JSON -> Java Objects
  }

  private Cart(Builder builder) {
    this.totalPrice = builder.totalPrice;
    this.currencyCode = builder.currencyCode;
    this.lineItems = builder.lineItems;
  }

  public String getTotalPrice() {
    return totalPrice;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public List<LineItem> getLineItems() {
    return lineItems;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Builder toBuilder() {
    return newBuilder()
        .setTotalPrice(totalPrice)
        .setCurrencyCode(currencyCode)
        .setLineItems(lineItems);
  }

  /**
   * A class that helps creating the Cart.
   */
  public static class Builder {
    private String totalPrice;
    private String currencyCode;
    private List<LineItem> lineItems;

    private Builder() {
    }

    public Builder setTotalPrice(String totalPrice) {
      this.totalPrice = totalPrice;
      return this;
    }

    public Builder setCurrencyCode(String currencyCode) {
      this.currencyCode = currencyCode;
      return this;
    }

    public Builder setLineItems(List<LineItem> lineItems) {
      this.lineItems = lineItems;
      return this;
    }

    public Builder addLineItem(LineItem lineItem) {
      if (lineItems == null) {
        this.lineItems = new ArrayList<LineItem>();
      }
      this.lineItems.add(lineItem);
      return this;
    }

    public Cart build() {
      // validate and return
      return new Cart(this);
    }
  }
}
