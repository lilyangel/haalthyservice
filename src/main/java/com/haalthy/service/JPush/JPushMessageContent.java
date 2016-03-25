package com.haalthy.service.JPush;

import java.util.Map;

/**
 * Created by Ken on 2016-01-08.
 */
public class JPushMessageContent {
        private String fromUserName;
        private byte[] content;
        private Map<String,String> extras;

        public String getFromUserName() {
            return fromUserName;
        }

        public void setFromUserName(String fromUserName) {
            this.fromUserName = fromUserName;
        }

        public byte[] getContent() {
            return content;
        }

        public void setContent(byte[] content) {
            this.content = content;
        }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }
}
