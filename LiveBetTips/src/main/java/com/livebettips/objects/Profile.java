package com.livebettips.objects;

import java.util.HashMap;
import java.util.Map;


public class Profile {

        private Integer id;
        private String username;
        private String authToken;
        private Integer usercredit;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Profile withId(Integer id) {
            this.id = id;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Profile withUsername(String username) {
            this.username = username;
            return this;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public Profile withAuthToken(String authToken) {
            this.authToken = authToken;
            return this;
        }

        public Integer getCredit() {
        return usercredit;
    }

        public void setCredit(Integer credit) {
        this.usercredit = credit;
    }


    public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

