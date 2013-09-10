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

package com.google.imaginary.server.config;

import javax.servlet.http.HttpServletRequest;

/**
 * Static configuration class that handles constants.
 * We recommend not using this method and storing your keys in a secure area.
 * Available environments:
 * SANDBOX - Test environment where no real world transactions are processed.
 * PRODUCTION -Production environment with real credit cards.
 */
public class Config {

  public static final Config SANDBOX = new Config("Sandbox",
      System.getProperty("sandbox_merchant_id"),
      System.getProperty("sandbox_merchant_auth_key"),
      System.getProperty("sandbox_merchant_js"),
      System.getProperty("sandbox_scopes"));
  public static final Config PRODUCTION = new Config("Production",
      System.getProperty("production_merchant_id"),
      System.getProperty("production_merchant_auth_key"),
      System.getProperty("merchant_js"),
      System.getProperty("scopes"));

  private final String id;
  private final String key;
  private final String url;
  private final String name;
  private final String scopes;
  public static final String MERCHANT_NAME = System.getProperty("merchant_name");
  public static final String OAUTH_CLIENT_ID = System.getProperty("oauth_client_id");
  public static final String OAUTH_API_KEY = System.getProperty("oauth_api_key");

  // Set the environment that you're deploying against
  private static Config environment =
      System.getProperty("online_wallet_enviroment").equalsIgnoreCase("SANDBOX") ? SANDBOX
          : PRODUCTION;

  public static Config getEnvironment() {
    return environment;
  }

  protected static void setEnvironment(Config environment) {
    Config.environment = environment;
  }

  Config(String name, String id, String key, String url, String scopes) {
    this.name = name;
    this.id = id;
    this.key = key;
    this.url = url;
    this.scopes = scopes;
  }

  /**
   * Helper function to get Merchant Id based on the configured environment.
   *
   * @return Merchant unique Id.
   */
  public String getMerchantId() {
    return id;
  }

  /**
   * Helper function to get Merchant Secret based on the configured environment.
   *
   * @return Merchant secret key.
   */
  public String getMerchantSecret() {
    return key;
  }

  /**
   * Helper function to get the Wallet JS URL based on environment.
   * To run for development against a local server you should use getDevJsUrl.
   *
   * @return Wallet JS URL.
   */
  public String getJsUrl() {
    return url;
  }

  /**
   * The nice name for the environment, this is added to the title of each page
   * to indicate which environment you are using.
   *
   * @return Name of environment.
   */
  public String getName() {
    return name;
  }

  /**
   * Scopes for Single sign on feature, which
   * enables your customers to authenticate themselves to your website using their
   * Google credentials.
   *
   * @return Scopes for Single sign on.
   */
  public String getScopes() {
    return scopes;
  }

  @Override
  public String toString() {
    return getName();
  }

  // Request currency
  public static final String CURRENCY = "USD";

  /**
   * Helper function to return the protocol://domain:port.
   *
   * @param req servlet request object.
   * @return request protocol://domain:port.
   */
  public static String getDomain(HttpServletRequest req) {
    String port = Integer.toString(req.getServerPort());
    String origin = req.getScheme() + "://" + req.getServerName();
    if (!(port.equals("80") || port.equals("443"))) {
      origin += ":" + port;
    }
    return origin;
  }
}

