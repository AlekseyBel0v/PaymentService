package com.belov.paymentservice.response_body;

import lombok.Getter;

@Getter
public class ResponseBodyError {
        private String message;
        private int id = 0;

        public ResponseBodyError() {
        }

        public ResponseBodyError(String message) {
            this.message = message;
        }
}
