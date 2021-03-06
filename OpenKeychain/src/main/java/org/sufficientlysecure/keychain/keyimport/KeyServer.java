/*
 * Copyright (C) 2012-2014 Dominik Schürmann <dominik@dominikschuermann.de>
 * Copyright (C) 2011-2014 Thialfihar <thi@thialfihar.org>
 * Copyright (C) 2011 Senecaso
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

package org.thialfihar.android.apg.keyimport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class KeyServer {
    public static class QueryException extends Exception {
        private static final long serialVersionUID = 2703768928624654512L;

        public QueryException(String message) {
            super(message);
        }
    }

    public static class TooManyResponses extends Exception {
        private static final long serialVersionUID = 2703768928624654513L;
    }

    public static class InsufficientQuery extends Exception {
        private static final long serialVersionUID = 2703768928624654514L;
    }

    public static class AddKeyException extends Exception {
        private static final long serialVersionUID = -507574859137295530L;
    }

    abstract List<ImportKeysListEntry> search(String query) throws QueryException, TooManyResponses,
            InsufficientQuery;

    abstract public String get(String keyIdHex) throws QueryException;

    abstract public void add(String armoredKey) throws AddKeyException;

    public static String readAll(InputStream in, String encoding) throws IOException {
        ByteArrayOutputStream raw = new ByteArrayOutputStream();

        byte buffer[] = new byte[1 << 16];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            raw.write(buffer, 0, n);
        }

        if (encoding == null) {
            encoding = "utf8";
        }
        return raw.toString(encoding);
    }
}
