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

package am.ik.voicetext4j;


import java.io.Serializable;

import am.ik.voicetext4j.http.VoiceTextFields;
import am.ik.voicetext4j.http.VoiceTextResponse;
import am.ik.voicetext4j.http.VoiceTextUrlConnectionClient;

@SuppressWarnings("unchecked")
public abstract class VoiceContext<T extends VoiceContext> implements Serializable {
    final String speaker;
    int pitch = 100;
    int speed = 100;
    int volume = 100;

    public VoiceContext(final String speaker) {
        this.speaker = speaker;
    }

    protected VoiceTextFields build() {
        return new VoiceTextFields()
                .put("speaker", this.speaker)
                .put("pitch", String.valueOf(this.pitch))
                .put("speed", String.valueOf(this.speed))
                .put("volume", String.valueOf(this.volume));
    }

    public VoiceTextResponse getResponse(final String text) {
        return this.getResponse(text, System.getProperty("voicetext.apikey"));
    }

    public VoiceTextResponse getResponse(final String text, final String apiKey) {
        if (text == null || text.length() < 1 || text.length() > 200) {
            throw new IllegalArgumentException("the length of 'text' must be between 1 and 200.");
        }
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("'apiKey' is required.");
        }
        final VoiceTextFields fields = this.build().put("text", text);
        return new VoiceTextUrlConnectionClient()
                .execute(fields, apiKey);
    }

    public T pitch(final int pitch) {
        if (pitch < 50 || pitch > 200) {
            throw new IllegalArgumentException("'pitch' must be between 50 and 200.");
        }
        this.pitch = pitch;
        return (T) this;
    }

    public T speed(final int speed) {
        if (speed < 50 || speed > 400) {
            throw new IllegalArgumentException("'speed' must be between 50 and 400.");
        }
        this.speed = speed;
        return (T) this;
    }

    public T volume(final int volume) {
        if (volume < 50 || volume > 200) {
            throw new IllegalArgumentException("'volume' must be between 50 and 200.");
        }
        this.volume = volume;
        return (T) this;
    }

    // public CompletableFuture<Void> speak(String text, String apiKey) throws InterruptedException {
    // return getResponse(text, apiKey).play();
    // }
    //
    // public CompletableFuture<Void> speak(String text) {
    // return getResponse(text).play();
    // }
}
