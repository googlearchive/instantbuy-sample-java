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

import java.io.Serializable;

/**
 * A simple class known for its static methods and classes.
 *
 */
public class JwtRequests implements Serializable{

  public static final String MASKED_WALLET_REQ_TYP = "google/wallet/online/masked/v2/request";
  public static final String FULL_WALLET_REQ_TYP = "google/wallet/online/full/v2/request";
  public static final String TRANSACTION_STATUS_REQ = "google/wallet/online/transactionstatus/v2";

  public static final String DEFAULT_AUDIENCE = "Google";
  public static final String DEFAULT_TYPE = "JWT";

  // Class known for its static members
  private JwtRequests() {}

  /**
   * Utility method to create a Builder object for MaskedWalletRequestContainer.
   * @return Builder
   */
  public static MaskedWalletContainer.Builder newMaskedWalletBuilder() {
    return new MaskedWalletContainer.Builder()
        .setTyp(MASKED_WALLET_REQ_TYP)
        .setAud(DEFAULT_AUDIENCE);
  }

  /**
   * Utility method to create a Builder object for FullWalletRequestContainer.
   * @return Builder
   */
  public static FullWalletContainer.Builder newFullWalletBuilder() {
    return new FullWalletContainer.Builder()
        .setTyp(FULL_WALLET_REQ_TYP)
        .setAud(DEFAULT_AUDIENCE);
  }


  /**
   * Utility method to create a Builder object for TransactionStatusContainer.
   * @return Builder
   */
  public static TransactionStatusContainer.Builder newTransactionStatusBuilder() {
    return new TransactionStatusContainer.Builder()
        .setTyp(TRANSACTION_STATUS_REQ)
        .setAud(DEFAULT_AUDIENCE);
  }


  /**
   * A simple class that represents the Masked Wallet Container.
   */
  public static class MaskedWalletContainer extends BaseJwt implements Serializable{
    private MaskedWalletRequest request;

    public MaskedWalletContainer() {

    }

    public MaskedWalletContainer(Builder builder) {
      super(builder);
      this.request = builder.request;
    }

    public MaskedWalletRequest getRequest() {
      return request;
    }

    /**
     * Helper class to create a MaskedWalletContainer.
     */
    public static class Builder extends BaseJwt.Builder<Builder> {
      private MaskedWalletRequest request;

      @Override
      protected Builder self() {
        return this;
      }

      public Builder setRequest(MaskedWalletRequest request) {
        this.request = request;
        return this;
      }

      public MaskedWalletContainer build() {
        return new MaskedWalletContainer(this);
      }

    }
  }

  /**
   * A simple class that represents the Full Wallet Request container.
   */
  public static class FullWalletContainer extends BaseJwt implements Serializable{
    private FullWalletRequest request;

    public FullWalletContainer() {

    }

    public FullWalletContainer(Builder builder) {
      super(builder);
      this.request = builder.request;
    }

    public FullWalletRequest getRequest() {
      return request;
    }

    /**
     * Helper class to create a FullWalletContainer.
     */
    public static class Builder extends BaseJwt.Builder<Builder> {
      private FullWalletRequest request;

      @Override
      protected Builder self() {
        return this;
      }

      public Builder setRequest(FullWalletRequest request) {
        this.request = request;
        return this;
      }

      public FullWalletContainer build() {
        return new FullWalletContainer(this);
      }
    }
  }

  /**
   * A simple class that represents the Transaction Status Notification request.
   */
  public static class TransactionStatusContainer extends BaseJwt implements Serializable{
    private TransactionStatusNotification request;

    public TransactionStatusContainer() {

    }

    public TransactionStatusContainer(Builder builder) {
      super(builder);
      this.request = builder.request;
    }

    public TransactionStatusNotification getRequest() {
      return request;
    }

    /**
     * Helper class to build a TransactionStatusContainer.
     */
    public static class Builder extends BaseJwt.Builder<Builder> {
      private TransactionStatusNotification request;

      @Override
      protected Builder self() {
        return this;
      }

      public Builder setRequest(TransactionStatusNotification request) {
        this.request = request;
        return this;
      }

      public TransactionStatusContainer build() {
        return new TransactionStatusContainer(this);
      }
    }
  }
}
