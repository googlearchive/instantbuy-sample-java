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

package com.google.imaginary.server;

import com.google.imaginary.server.config.Config;
import com.google.wallet.online.jwt.JwtRequests;
import com.google.wallet.online.jwt.JwtRequests.MaskedWalletContainer;
import com.google.wallet.online.jwt.MaskedWalletRequest;
import com.google.wallet.online.jwt.Pay;
import com.google.wallet.online.jwt.Ship;
import com.google.wallet.online.jwt.util.JwtGenerator;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet generates the MaskedWalletRequest based on parameters passed in the HTTP request.
 */
public class MaskedWalletRequestServlet extends HttpServlet {

  private static final Logger logger =
      Logger.getLogger(MaskedWalletRequestServlet.class.getSimpleName());

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    getMaskedWallet(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    getMaskedWallet(req, resp);
  }

  /**
   * The following parameters are parsed:
   * Total - Order total.
   * Currency - Order currency.
   * Gid - Google order id for any subsequent.
   * MaskedWalletRequest after the initial request
   * Pnr - Phone number required boolean.
   * @param req {@link HttpServletRequest}
   * @param resp {@link HttpServletResponse}
   */
  private void getMaskedWallet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    Date date = new Date();
    String totalPrice = req.getParameter("total");

    // Create MaskedWalletRequest JWT
    MaskedWalletContainer maskedWalletContainer = JwtRequests.newMaskedWalletBuilder()
        .setTyp(JwtRequests.DEFAULT_TYPE)
        .setIat(date.getTime())
        .setExp(date.getTime() + 3600)
        .setTyp(JwtRequests.MASKED_WALLET_REQ_TYP)
        .setAud(JwtRequests.DEFAULT_AUDIENCE)
        .setIss(Config.getEnvironment().getMerchantId())
        .setRequest(MaskedWalletRequest.newBuilder()
            .setClientId(Config.OAUTH_CLIENT_ID)
            .setOrigin(Config.getDomain(req))
            .setMerchantName(Config.MERCHANT_NAME)
            .setGoogleTransactionId(req.getParameter("gid"))
            .setShip(new Ship())
            .setPay(Pay.newBuilder()
                .setCurrencyCode(Config.CURRENCY)
                .setEstimatedTotalPrice(totalPrice).build())
            .build()).build();

    try {
      // Sign the JWT
      String signedJwt = JwtGenerator.javaToJWT(
          maskedWalletContainer, Config.getEnvironment().getMerchantSecret());
      // send the JWT
      resp.getWriter().print(signedJwt);
    } catch (InvalidKeyException ex) {
      logger.log(Level.SEVERE, "Invalid key exception ", ex);
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } catch (SignatureException ex) {
      logger.log(Level.SEVERE, "Signature exception ", ex);
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

}
