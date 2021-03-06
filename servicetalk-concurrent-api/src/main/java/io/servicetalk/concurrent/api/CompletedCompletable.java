/*
 * Copyright © 2018 Apple Inc. and the ServiceTalk project authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.servicetalk.concurrent.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.servicetalk.concurrent.Cancellable.IGNORE_CANCEL;
import static io.servicetalk.concurrent.internal.SubscriberUtils.handleExceptionFromOnSubscribe;

final class CompletedCompletable extends AbstractSynchronousCompletable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompletedCompletable.class);
    static final CompletedCompletable INSTANCE = new CompletedCompletable();

    private CompletedCompletable() {
        // singleton
    }

    @Override
    void doSubscribe(final Subscriber subscriber) {
        try {
            subscriber.onSubscribe(IGNORE_CANCEL);
        } catch (Throwable t) {
            handleExceptionFromOnSubscribe(subscriber, t);
            return;
        }
        try {
            subscriber.onComplete();
        } catch (Throwable t) {
            LOGGER.info("Ignoring exception from onComplete of Subscriber {}.", subscriber, t);
        }
    }
}
