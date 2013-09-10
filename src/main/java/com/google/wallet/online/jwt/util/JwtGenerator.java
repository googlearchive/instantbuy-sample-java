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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.wallet.online.jwt.BaseJwt;
import net.oauth.jsontoken.Clock;
import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.SystemClock;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProvider;
import net.oauth.jsontoken.discovery.VerifierProviders;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Helper class to convert Java Objects representations to JSON Web Tokens.
 *
 */
public class JwtGenerator {

  public static final long EXPIRATION_DELTA = 60 * 60 * 1000L;

  /**
   * Default Constructor.
   */
  private JwtGenerator() {
  }

  /**
   * Generates the Json Web Token (JWT) based on BaseJWT object provided.
   * @param target the BaseJWT object to convert into a JWT string.
   * @return Signed Json Web Token.
   * @throws InvalidKeyException
   * @throws SignatureException
   */
  public static String javaToJWT(BaseJwt target, String merchantSecret) throws
      InvalidKeyException, SignatureException {

    Clock clock = new SystemClock();

    JsonObject jsonData = GsonHelper.getGson().toJsonTree(target).getAsJsonObject();

    // Get signer for JWT
    HmacSHA256Signer signer = new HmacSHA256Signer(target.getIss(), null,
        merchantSecret.getBytes());

    // Create new JWT and set params
    JsonToken token = new JsonToken(signer);


    // Get the payload object to modify
    JsonObject payload = token.getPayloadAsJsonObject();

    Set<Map.Entry<String, JsonElement>> params = jsonData.entrySet();

    // Iterate through HashMap adding each item to the payload
    for (Map.Entry<String, JsonElement> param : params) {
      payload.add(param.getKey(), param.getValue());
    }

    if (token.getIssuedAt() == null) {
      token.setIssuedAt(clock.now());
    }
    if (token.getExpiration() == null) {
      token.setExpiration(clock.now().plus(EXPIRATION_DELTA));
    }

    return token.serializeAndSign();
  }

  /**
   * @param jwtClass Class to marshal it into.
   * @param jwt string jwt.
   * @return T instance of Class.
   */
  public static <T> T jwtToJava(Class<T> jwtClass, String jwt,
                                String secret) throws InvalidKeyException, SignatureException {
    JsonToken jt = jwtToJsonToken(jwt, secret);

    JsonObject json = jt.getPayloadAsJsonObject();

    return GsonHelper.getGson().fromJson(json, jwtClass);
  }

  /**
   * Converts a String Jwt to a JsonToken object.
   * @param jwt.
   * @param secret key.
   * @return a JsonToken.
   * @throws InvalidKeyException
   * @throws SignatureException
   */
  public static JsonToken jwtToJsonToken(String jwt, String secret)
      throws InvalidKeyException, SignatureException {

    final Verifier hmacVerifier = new HmacSHA256Verifier(secret.getBytes());
    VerifierProvider hmacLocator = new VerifierProvider() {

      @Override
      public List<Verifier> findVerifier(String id, String key) {
        return Arrays.asList(hmacVerifier);
      }
    };

    VerifierProviders locators = new VerifierProviders();
    locators.setVerifierProvider(SignatureAlgorithm.HS256, hmacLocator);

    // Ignore Audience does not mean that the Signature is ignored
    JsonTokenParser parser = new JsonTokenParser(locators, new IgnoreAudience());
    return parser.verifyAndDeserialize(jwt);
  }
}
