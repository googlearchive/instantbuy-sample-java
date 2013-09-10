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
import com.google.wallet.online.jwt.util.JwtGenerator;

import net.oauth.jsontoken.JsonToken;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet checks the JWT's signature to ensure that it's valid. true or false is returned once
 * the JWT has been checked.
 */
public class JwtValidatorServlet extends HttpServlet {
  
  private static final Logger logger = Logger.getLogger(JwtValidatorServlet.class.getSimpleName());

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    jwtValidator(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    jwtValidator(req, resp);
  }

  /**
   * The following parameters are parsed:
   * jwt - the jwt to validate
   * @param req {@link HttpServletRequest}
   * @param resp {@link HttpServletResponse}
   * @throws IOException 
   */
  private void jwtValidator(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String jwt = req.getParameter("jwt");
    PrintWriter pw = null;
    try {
      pw = resp.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      JsonToken jsonToken =
          JwtGenerator.jwtToJsonToken(jwt, Config.getEnvironment().getMerchantSecret());
      pw.write("true");
    } catch (NullPointerException e) {
      logger.log(Level.SEVERE, "Null Pointer Exception", e);
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } catch (InvalidKeyException e) {
      pw.write("false");
    } catch (SignatureException e) {
      pw.write("false");
    }
  }

}
