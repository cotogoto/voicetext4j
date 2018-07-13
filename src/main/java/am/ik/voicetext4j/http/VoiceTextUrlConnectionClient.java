/*
 * Copyright (C) 2014-2016 Toshiaki Maki <makingx@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Copyright (C) 2014 Toshiaki Maki <makingx@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package am.ik.voicetext4j.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.DatatypeConverter;

public class VoiceTextUrlConnectionClient {
    static final int CONNECT_TIMEOUT_MILLIS = 15 * 1000; // 15s
    static final int READ_TIMEOUT_MILLIS = 20 * 1000; // 20s
    static final int CHUNK_SIZE = 4096;
    static final String API_ENDPOINT = System.getProperty("voicetext.endpoint", "https://api.voicetext.jp/v1/tts");

    public VoiceTextResponse execute(final VoiceTextFields fields, final String apiKey) {
        try {
            final HttpURLConnection connection = this.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + DatatypeConverter.printBase64Binary((apiKey + ":").getBytes()));
            this.prepareRequest(connection, fields);
            return new VoiceTextResponse(this.readResponse(connection));
        } catch (final IOException e) {
            throw new VoiceTextIllegalStateException(e);
        }
    }

    HttpURLConnection openConnection() throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL(VoiceTextUrlConnectionClient.API_ENDPOINT).openConnection();
        connection.setConnectTimeout(VoiceTextUrlConnectionClient.CONNECT_TIMEOUT_MILLIS);
        connection.setReadTimeout(VoiceTextUrlConnectionClient.READ_TIMEOUT_MILLIS);

        return connection;
    }


    void prepareRequest(final HttpURLConnection connection, final VoiceTextFields fieldsBuilder) throws IOException {
        connection.setRequestMethod("POST");
        connection.setChunkedStreamingMode(VoiceTextUrlConnectionClient.CHUNK_SIZE);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        connection.setRequestProperty("User-Agent", "VoiceText4J");
        connection.setDoOutput(true);
        connection.getOutputStream().write(fieldsBuilder.getBody());
    }

    InputStream readResponse(final HttpURLConnection connection) throws IOException {
        final int status = connection.getResponseCode();
        String reason = connection.getResponseMessage();
        if (reason == null)
        {
            reason = ""; // HttpURLConnection treats empty reason as null.
        }
        // InputStream stream;

        if (status >= 400) {
            throw new VoiceTextApiCallException(status, reason);
        } else {
            return connection.getInputStream();
        }

        // try {
        // return stream;
        // } catch (final UnsupportedAudioFileException e) {
        // throw new VoiceTextIllegalStateException(e);
        // }
    }
}
