/*
 * Copyright 2017 Ken Lowrie.
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
package clsrestapi;

/**
 * The caller references these constants using <tt>Constants.WSURL</tt>.
 *
 */
public class Constants {
  
  public static final String NL = System.getProperty("line.separator");
  public static final String WSURL = "http://api.cloudylogic.com";
  public static final String API_ABOUT_US = "about-us";
  public static final String API_CONTACT_INFO = "contact-info";
  public static final String API_REELS = "reels";
  public static final String API_OUR_WORK = "our-work";
  public static final String API_VERSIONS = "versions";

  /**
   * Prevent the caller from constructing objects of this class.
  */
  private Constants(){
  }
}

