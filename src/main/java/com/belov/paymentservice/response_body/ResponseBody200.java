package com.belov.paymentservice.response_body;

import lombok.Getter;
import lombok.Setter;

public class ResponseBody200 {
        @Getter
        @Setter
        private String operationId;

        public ResponseBody200() {
        }

        public ResponseBody200(String operationId) {
            this.operationId = operationId;
        }
}
