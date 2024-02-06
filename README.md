# VoiceText4J
Java Client Library for [VoiceText Web API](https://cloud.voicetext.jp/webapi)

[![Build Status](https://travis-ci.org/making/voicetext4j.svg?branch=master)](https://travis-ci.org/making/voicetext4j)

※こちらのライブラリは、音声をInputStreamでそのまま取得できるように改造しています。
※Java11に対応

## Download
[![](https://jitpack.io/v/cotogoto/voicetext4j.svg)](https://jitpack.io/#cotogoto/voicetext4j)

Be sure to replace the **VERSION** key below with the latest version listed above.

Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependency>
    <groupId>com.github.cotogoto</groupId>
    <artifactId>voicetext4j</artifactId>
    <version>VERSION</version>
</dependency>
```

## Usage

    import org.junit.Test;
    import am.ik.voicetext4j.*;

    public class SpeakerTest {

        @Test
        public void testSay() throws Exception {
            System.setProperty("voicetext.apikey", "API_KEY");

            InputStream is = EmotionalSpeaker.HARUKA.ready()
                    .pitch(105)
                    .speed(105)
                    .very().happy()
                    .getResponse("こんにちは")
                    .inputStream();

            // you can use getResponse(text, "API_KEY") instead of using System.setProperty("voicetext.apikey", "API_KEY")
        }
    }


## Quick Start using Groovy and Grape

`test.groovy`

    @Grab("am.ik.voicetext:voicetext4j:0.12.0")
    import am.ik.voicetext4j.*;

    System.setProperty("voicetext.apikey", "API_KEY");

    Speaker.SHOW.ready().speak("こんにちは");
    EmotionalSpeaker.HARUKA.ready().speak("こんにちは");
    EmotionalSpeaker.HIKARI.ready().speak("こんにちは");
    EmotionalSpeaker.TAKERU.ready().speak("こんにちは");
    EmotionalSpeaker.SANTA.ready().speak("メリークリスマス"); // new speaker from 0.11.0
    EmotionalSpeaker.BEAR.ready().speak("こんにちは").get(); // new speaker from 0.12.0

run

    $ groovy test.groovy

### Change emotion

`EmotionalSpeaker`s can be changed their emotion like the following:

    EmotionalSpeaker.HARUKA.ready().speak("こんにちは");
    EmotionalSpeaker.HARUKA.ready().angry().speak("こんにちは");
    EmotionalSpeaker.HARUKA.ready().very().angry().speak("こんにちは");
    EmotionalSpeaker.HARUKA.ready().happy().speak("こんにちは");
    EmotionalSpeaker.HARUKA.ready().very().happy().speak("こんにちは");
    EmotionalSpeaker.HARUKA.ready().sad().speak("こんにちは");
    EmotionalSpeaker.HARUKA.ready().very().sad().speak("こんにちは");

## License

Licensed under the Apache License, Version 2.0.
