/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  The ASF licenses this file to You
* under the Apache License, Version 2.0 (the "License"); you may not
* use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.  For additional information regarding
* copyright in this work, please see the NOTICE file in the top level
* directory of this distribution.
*/
package org.apache.abdera.server.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Provides a utility class helpful for determining which type of resource
 * the client is requesting.  Each resource type (e.g. service doc, collection,
 * entry, edit uri, media resource, etc) is assigned a regex pattern.  Given
 * the request URI (path and querystring), this will determine which resource
 * was selected and return an appropriate TargetMatcher.  TargetMatcher is 
 * essentially just a simplified version of the java.util.regex.Matcher that
 * also specifies the Resource Type.</p>
 * 
 * <pre>
 *  Target target = new Target();
 *  target.addPattern(ResourceType.INTROSPECTION, "/atom");
 *  target.addPattern(ResourceType.COLLECTION, "/atom/([^/#?]+)");
 *  target.addPattern(ResourceType.ENTRY, "/atom/([^/#?]+)/([^/#?]+)");
 *  target.addPattern(ResourceType.ENTRY_EDIT, "/atom/([^/#?]+)/([^/#?]+)\\?edit");
 *  target.addPattern(ResourceType.MEDIA,"/atom/([^/#?]+)/([^/#?]+)\\?media");
 *  target.addPattern(ResourceType.MEDIA_EDIT,"/atom/([^/#?]+)/([^/#?]+)\\?edit-media");
 *  
 *  TargetMatcher tm = target.getTargetMatcher("/atom/foo");
 *  System.out.println(tm.getType());
 *  System.out.println(tm.getToken(1));  // foo
 * </pre>
 *  
 */
public class Target {

  private Map<ResourceType,Pattern> patterns = null;
  
  public Target() {
    this.patterns = new HashMap<ResourceType,Pattern>();
  }
  
  public Target(Map<ResourceType,String> patterns) {
    this.patterns = new HashMap<ResourceType,Pattern>();
    for (ResourceType type : patterns.keySet()) {
      String p = patterns.get(type);
      Pattern pattern = Pattern.compile(p);
      this.patterns.put(type, pattern);
    }
  }
  
  public void addPattern(ResourceType type, String pattern) {
    Pattern p = Pattern.compile(pattern);
    this.patterns.put(type, p);
  }
  
  public TargetMatcher getTargetMatcher(String path_info) {
    if (patterns == null) return null;
    for (ResourceType type : patterns.keySet()) {
      Pattern pattern = patterns.get(type);
      Matcher matcher = pattern.matcher(path_info);
      if (matcher.matches()) return new TargetMatcher(type, matcher);
    }
    return null;
  }
  
  public static class TargetMatcher {
    
    Matcher matcher = null;
    ResourceType type = ResourceType.UNKNOWN;
    
    TargetMatcher(ResourceType type, Matcher matcher) {
      this.type = type;
      this.matcher = matcher;
    }
    
    public ResourceType getType() {
      return this.type;
    }
    
    public String getToken(int token) {
      try {
        return this.matcher.group(token);
      } catch (IndexOutOfBoundsException e) {
        return null;
      }
    }
    
    public boolean hasToken(int token) {
      return getToken(token) != null;
    }
  }
  
}
