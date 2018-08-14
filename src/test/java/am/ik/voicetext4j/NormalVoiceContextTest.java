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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NormalVoiceContextTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testBuild() throws Exception {
        byte[] param = new NormalVoiceContext("show")
                .build().getBody();
        assertThat(param, is("speaker=show&pitch=100&speed=100&volume=100&format=wav".getBytes()));
    }


    @Test
    public void testJapanese() throws Exception {
        byte[] param = new NormalVoiceContext("show")
                .build().getBody();
        assertThat(param, is("speaker=show&pitch=100&speed=100&volume=100&format=wav".getBytes()));
    }

    @Test
    public void testBuild_setAll() throws Exception {
        byte[] param = new NormalVoiceContext("show")
                .pitch(120)
                .speed(200)
                .volume(150)
                .build().getBody();
        assertThat(param, is("speaker=show&pitch=120&speed=200&volume=150&format=wav".getBytes()));
    }

    @Test
    public void testBuild_setAllMax() throws Exception {
        byte[] param = new NormalVoiceContext("show")
                .pitch(200)
                .speed(400)
                .volume(200)
                .build().getBody();
        assertThat(param, is("speaker=show&pitch=200&speed=400&volume=200&format=wav".getBytes()));
    }

    @Test
    public void testBuild_setAllMin() throws Exception {
        byte[] param = new NormalVoiceContext("show")
                .pitch(50)
                .speed(50)
                .volume(50)
                .build().getBody();
        assertThat(param, is("speaker=show&pitch=50&speed=50&volume=50&format=wav".getBytes()));
    }

    @Test
    public void testBuild_setLargePitch() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        new NormalVoiceContext("show")
                .pitch(201)
                .speed(200)
                .volume(200)
                .build().getBody();
    }


    @Test
    public void testBuild_setSmallPitch() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        new NormalVoiceContext("show")
                .pitch(49)
                .speed(400)
                .volume(200)
                .build().getBody();
    }

    @Test
    public void testBuild_setLargeSpeed() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        new NormalVoiceContext("show")
                .pitch(200)
                .speed(401)
                .volume(200)
                .build().getBody();
    }


    @Test
    public void testBuild_setSmallSpeed() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        new NormalVoiceContext("show")
                .pitch(200)
                .speed(49)
                .volume(200)
                .build().getBody();
    }


    @Test
    public void testBuild_setLargeVolume() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        new NormalVoiceContext("show")
                .pitch(200)
                .speed(400)
                .volume(201)
                .build().getBody();
    }


    @Test
    public void testBuild_setSmallVolume() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        new NormalVoiceContext("show")
                .pitch(200)
                .speed(400)
                .volume(49)
                .build().getBody();
    }
}