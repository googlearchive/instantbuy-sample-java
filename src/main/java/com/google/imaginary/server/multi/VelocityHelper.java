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

package com.google.imaginary.server.multi;

import com.google.imaginary.server.config.Config;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Velocity Templates are used for rendering the item details page.
 *
 * @see <a href="http://velocity.apache.org/engine/devel/developer-guide.html">Velocity Guide</a>
 *      This class handles the setup and tear down events which are common between all Servlets.
 */
public class VelocityHelper implements Serializable {
  private final PrintWriter printWriter;
  private final Config environment;
  public HttpSession session;
  private final HttpServletRequest req;
  private final HttpServletResponse resp;

  /**
   * Initialize the velocity configuration.
   */
  static void init() throws Exception {
    // Initialize Velocity Templates
    Properties velocityProperties = new Properties();
    velocityProperties.put("resource.loader", "class");
    velocityProperties.put(
        "class.resource.loader.description", "Velocity Classpath Resource Loader");
    velocityProperties.put("class.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    // So we don't write to velocity.log
    velocityProperties.put(
        "runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
    Velocity.init(velocityProperties);
  }

  /**
   * Constructor for VelocityHelper.
   *
   * @param req {@link HttpServletRequest}
   * @param resp {@link HttpServletResponse}
   * @param environment
   * @throws IOException
   */
  public VelocityHelper(HttpServletRequest req, HttpServletResponse resp, Config environment)
      throws IOException {
    this.environment = environment;
    this.session = req.getSession();
    this.req = req;
    this.resp = resp;
    // Get the response output stream PrintWriter.
    this.printWriter = resp.getWriter();
  }

  /**
   * @return the configured environment.
   */
  public Config getEnvironment() {
    return environment;
  }

  /**
   * @param templateName the Velocity template to render.
   * @param context the context variables which the template requires, null is no context.
   */
  public void writeTemplate(String templateName, VelocityContext context) {
    if (context == null) {
      context = new VelocityContext();
    }
    try {
      Velocity.mergeTemplate(templateName, "UTF-8", context, printWriter);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the request parameter for the given key.
   *
   * @param key requested key
   */
  public void setCookie(String key, String value) {
    Cookie userCookie = new Cookie(key, value);
    resp.addCookie(userCookie);
  }

  /**
   * @param cookieName
   * @param defaultValue
   * @return String
   */
  public String getCookieValue(String cookieName, String defaultValue) {
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        Cookie cookie = cookies[i];
        if (cookieName.equals(cookie.getName())) {
          return (cookie.getValue());
        }
      }
    }
    return (defaultValue);
  }

  /**
   * Removes all the browser cookies.
   */
  public void removeCookies() {
    Cookie[] allCookies = req.getCookies();
    for (Cookie cookie : allCookies) {
      cookie.setMaxAge(0);
      resp.addCookie(cookie);
    }
  }

  /**
   * Get the request parameter for the given key.
   *
   * @param key requested key
   * @return string
   */
  public String getParameter(String key) {
    return req.getParameter(key);
  }

  public PrintWriter getPrintWriter() {
    return printWriter;
  }

  /**
   * @return the session
   */
  public HttpSession getSession() {
    return session;
  }

  /**
   * @return the request
   */
  public HttpServletRequest getReq() {
    return req;
  }


}
