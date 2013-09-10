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
 * This bean class represents cart line items for the FullWalletRequest. These line items
 * will be stored in receipts for customers to review.
 *
 *
 */
public class LineItem {
  private String description;
  private Integer quantity;
  private Double unitPrice;
  private Double totalPrice;
  private Role role;

  /**
   * Enumeration for the various Line item roles.
   */
  public enum Role {TAX, SHIPPING}

  public LineItem() {
    // Empty constructor used in Gson conversion of JSON -> Java Objects
  }

  // Constructor using builder
  private LineItem(Builder builder) {
    this.description = builder.description;
    this.quantity = builder.quantity;
    this.unitPrice = builder.unitPrice;
    this.totalPrice = builder.totalPrice;
    this.role = builder.role;
  }

  public String getDescription() {
    return description;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public Double getUnitPrice() {
    return unitPrice;
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public Role getRole() {
    return role;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Builder toBuilder() {
    return newBuilder()
        .setDescription(description)
        .setQuantity(quantity)
        .setRole(role)
        .setTotalPrice(totalPrice)
        .setUnitPrice(unitPrice);
  }

  /**
   * Helper class to create the LineItem.
   */
  public static class Builder {
    private String description;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
    private Role role;

    private Builder() {
    }

    public static Builder newBuilder() {
      return new Builder();
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder setQuantity(Integer quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder setUnitPrice(Double unitPrice) {
      this.unitPrice = unitPrice;
      return this;
    }

    public Builder setTotalPrice(Double totalPrice) {
      this.totalPrice = totalPrice;
      return this;
    }

    public Builder setRole(Role role) {
      this.role = role;
      return this;
    }

    public LineItem build() {
      if (this.quantity != null && this.unitPrice != null) {
        setTotalPrice(quantity * unitPrice);
      }
      return new LineItem(this);
    }
  }
}
