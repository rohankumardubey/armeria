/*
 * Copyright 2018 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
/*
 * Copyright 2014, gRPC Authors All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.linecorp.armeria.internal.common.grpc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.google.api.gax.grpc.GrpcStatusCode;

import com.linecorp.armeria.client.circuitbreaker.CircuitBreaker;
import com.linecorp.armeria.client.circuitbreaker.FailFastException;

import io.grpc.Status;

class GrpcStatusTest {
    @Test
    void grpcCodeToHttpStatus() {
        for (Status.Code code : Status.Code.values()) {
            assertThat(GrpcStatus.grpcCodeToHttpStatus(code).code())
                    .as("gRPC code: {}", code)
                    .isEqualTo(GrpcStatusCode.of(code).getCode().getHttpStatusCode());
        }
    }

    @Test
    void failFastExceptionToUnavailableCode() {
        assertThat(GrpcStatus.fromThrowable(new FailFastException(CircuitBreaker.ofDefaultName()))
                             .getCode())
                .isEqualTo(Status.Code.UNAVAILABLE);
    }
}
