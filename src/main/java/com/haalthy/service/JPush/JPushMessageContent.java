package com.haalthy.service.JPush;

/**
 * Created by Ken on 2016-01-08.
 */
public class JPushMessageContent {
        private String fromUserName;
        private byte[] content;

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
}
