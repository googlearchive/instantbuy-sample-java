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
package com.google.wallet.online.jwt.util;

import com.google.gson.JsonObject;

import net.oauth.jsontoken.Checker;

/**
 * Allows any audience (even null).  Allow any audience after the signature has
 * already been checked.
 */
public class IgnoreAudience implements Checker {

  @Override
  public void check(JsonObject payload) {
    // don't throw - allow anything
  }
}
