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

import com.google.gson.reflect.TypeToken;
import com.google.imaginary.server.config.Config;
import com.google.wallet.online.jwt.Cart;
import com.google.wallet.online.jwt.FullWalletRequest;
import com.google.wallet.online.jwt.JwtRequests;
import com.google.wallet.online.jwt.JwtRequests.FullWalletContainer;
import com.google.wallet.online.jwt.LineItem;
import com.google.wallet.online.jwt.util.GsonHelper;
import com.google.wallet.online.jwt.util.JwtGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet handles requests for the Full Wallet Request JWT. It parses the post parameters and
 * generates the Full Wallet Request JWT. Currently this servlet only handles a single item.
 *
 */
public class FullWalletRequestServlet extends HttpServlet {

  private static final Logger logger =
      Logger.getLogger(FullWalletRequestServlet.class.getSimpleName());

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    generateJwtContainer(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    generateJwtContainer(req, resp);
  }

  /**
   * The following parameters are parsed and are added to the container:
   * Description - Description of the object.
   * Quantity - Number of items purchased.
   * Unit price - Unit price of an item.
   * Per item gid - Google transaction ID.
   * @param req {@link HttpServletRequest}
   * @param resp {@link HttpServletResponse}
   * @throws IOException
   */
  private void generateJwtContainer(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String origin = Config.getDomain(req);

    String arrCart = req.getParameter("arrCart");
    String tax = req.getParameter("tax");
    String shipping = req.getParameter("shipping");
    double totalPrice = Double.parseDouble(req.getParameter("totalPrice"));

    Type collectionType = new TypeToken<List<LineItem>>(){}.getType();
    List<LineItem> itemList = GsonHelper.getGson().fromJson(arrCart, collectionType);
    itemList.add(LineItem.newBuilder()
        .setDescription("Tax")
        .setRole(LineItem.Role.TAX)
        .setTotalPrice(Double.parseDouble(tax))
        .build());
    itemList.add(LineItem.newBuilder()
        .setDescription("shipping detail")
        .setRole(LineItem.Role.SHIPPING)
        .setTotalPrice(Double.parseDouble(shipping))
        .build());
    Date date = new Date();

    FullWalletContainer container = JwtRequests.newFullWalletBuilder()
        .setTyp(JwtRequests.DEFAULT_TYPE)
        .setIat(date.getTime())
        .setExp(date.getTime() + 3600)
        .setTyp(JwtRequests.FULL_WALLET_REQ_TYP)
        .setAud(JwtRequests.DEFAULT_AUDIENCE)
        .setIss(Config.getEnvironment().getMerchantId())
        .setRequest(FullWalletRequest.newBuilder()
            .setOrigin(origin)
            .setClientId(Config.OAUTH_CLIENT_ID)
            .setGoogleTransactionId(req.getParameter("gid"))
            .setMerchantName(Config.MERCHANT_NAME)
            .setCart(Cart.newBuilder()
                .setTotalPrice(Double.toString(totalPrice))
                .setCurrencyCode(Config.CURRENCY)
                .setLineItems(itemList)
                .build())
            .build()).build();

    PrintWriter pw = null;
    try {

      pw = resp.getWriter();
      String str = JwtGenerator.javaToJWT(container, Config.getEnvironment().getMerchantSecret());
      pw.write(str);
    } catch (InvalidKeyException e) {

      logger.log(Level.SEVERE, "Invalid key exception ", e);
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

    } catch (SignatureException e) {

      logger.log(Level.SEVERE, "Signature Exception", e);
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

    }
  }

}
