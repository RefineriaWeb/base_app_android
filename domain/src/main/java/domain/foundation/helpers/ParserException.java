/*
 * Copyright 2015 RefineriaWeb
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

package domain.foundation.helpers;

import javax.inject.Inject;

import domain.foundation.UseCase;
import domain.sections.UI;
import rx.Observable;
import rx.exceptions.CompositeException;

/**
 * Created by victor on 09/03/16.
 */
public class ParserException extends UseCase<String> {
    private Throwable throwable;

    @Inject public ParserException(UI ui) {
        super(ui);
    }

    public ParserException with(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    @Override public Observable<String> react() {
        String message = throwable.getMessage();

        if (throwable instanceof CompositeException) {
            message += System.getProperty("line.separator");
            CompositeException compositeException = (CompositeException) throwable;

            for (Throwable exception : compositeException.getExceptions()) {
                String exceptionName = exception.getClass().getSimpleName();
                String exceptionMessage = exception.getMessage() != null ? exception.getMessage() : "";
                message += exceptionName + " -> " + exceptionMessage + System.getProperty("line.separator");
            }
        }

        return Observable.just(message);
    }
}
