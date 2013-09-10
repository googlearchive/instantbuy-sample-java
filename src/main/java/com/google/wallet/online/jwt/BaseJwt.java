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
 * Base JWT object that contains the common parameters such as 
 * Issuer, audience, type, issued date and expiration.
 */
public abstract class BaseJwt {
  protected String iss;
  protected String aud;
  protected String typ;
  protected Long iat;
  protected Long exp;

  public BaseJwt() {
    // Empty constructor used in Gson conversion of JSON -> Java Objects
  }

  protected BaseJwt(Builder<? extends Builder<?>> builder) {
    this.iss = builder.iss;
    this.aud = builder.aud;
    this.typ = builder.typ;
    this.iat = builder.iat;
    this.exp = builder.exp;
  }

  public String getIss() {
    return iss;
  }

  public String getAud() {
    return aud;
  }

  public String getTyp() {
    return typ;
  }

  public Long getIat() {
    return iat;
  }

  public Long getExp() {
    return exp;
  }

  /**
   * A builder associated with any BaseJWT .
   */
  abstract static class Builder<T extends Builder<T>> {

    protected String iss;
    protected String aud;
    protected String typ;
    protected Long iat;
    protected Long exp;

    protected Builder() {
    }

    public T setIss(String iss) {
      this.iss = iss;
      return self();
    }

    public T setAud(String aud) {
      this.aud = aud;
      return self();
    }

    public T setTyp(String typ) {
      this.typ = typ;
      return self();
    }

    public T setIat(Long iat) {
      this.iat = iat;
      return self();
    }

    public T setExp(Long exp) {
      this.exp = exp;
      return self();
    }

    protected abstract T self();
  }
}
