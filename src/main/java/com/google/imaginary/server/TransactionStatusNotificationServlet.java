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
import com.google.wallet.online.jwt.JwtRequests.TransactionStatusContainer;
import com.google.wallet.online.jwt.TransactionStatusNotification;
import com.google.wallet.online.jwt.util.JwtGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet generates the TransactionStatusNotification. It takes a post parameter gid.
 */
public class TransactionStatusNotificationServlet extends HttpServlet {

  private static final Logger logger =
      Logger.getLogger(TransactionStatusNotificationServlet.class.getSimpleName());

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    getTransactionContainer(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    getTransactionContainer(req, resp);
  }

  /**
   * The following parameters are parsed:
   * gid - Google Transaction Id.
   * @param req {@link HttpServletRequest}
   * @param resp {@link HttpServletResponse}
   */
  private void getTransactionContainer(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    // Get Google Id
    String googleId = req.getParameter("gid");

    Date date = new Date();

    // Generate TransactionStatusNotification JWT
    TransactionStatusContainer container =
        JwtRequests.newTransactionStatusBuilder()
        .setIat(date.getTime())
        .setExp(date.getTime() + 3600)
        .setTyp(JwtRequests.TRANSACTION_STATUS_REQ)
        .setAud(JwtRequests.DEFAULT_AUDIENCE)
        .setIss(Config.getEnvironment().getMerchantId())
        .setRequest(TransactionStatusNotification.newBuilder()
            .setMerchantName(Config.MERCHANT_NAME)
            .setGoogleTransactionId(googleId)
            .setStatus(TransactionStatusNotification.Status.SUCCESS).build()).build();

    // Respond to request
    PrintWriter pw = null;
    try {
      pw = resp.getWriter();
      pw.write(JwtGenerator.javaToJWT(container, Config.getEnvironment().getMerchantSecret()));
    } catch (InvalidKeyException e) {
      logger.log(Level.SEVERE, "Invalid key exception ", e);
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } catch (SignatureException e) {
      logger.log(Level.SEVERE, "Invalid Signature exception ", e);
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

}
