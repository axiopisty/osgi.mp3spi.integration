package com.github.axiopisty.osgi.mp3spi.integration.mp3player.internal;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MP3Player implements Runnable {

  private final AudioInputStream decodedAudioInputStream;
  private final AudioFormat decodedAudioFormat;
  private final SourceDataLine line;

  public MP3Player(File mp3) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
    final AudioInputStream in = AudioSystem.getAudioInputStream(mp3);
    final AudioFormat format = in.getFormat();
    decodedAudioInputStream = AudioSystem.getAudioInputStream(new AudioFormat(
      AudioFormat.Encoding.PCM_SIGNED,
      format.getSampleRate(),
      16,
      format.getChannels(),
      format.getChannels() * 2,
      format.getSampleRate(),
      false
    ), in);
    decodedAudioFormat = decodedAudioInputStream.getFormat();
    DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedAudioFormat, AudioSystem.NOT_SPECIFIED);
    line = (SourceDataLine) AudioSystem.getLine(info);
  }

  @Override
  public void run() {
    try {
      openLine();
      int bytes = 1;
      byte[] buffer = new byte[16000];
      while ((bytes != -1)) {
        try {
          bytes = decodedAudioInputStream.read(buffer, 0, buffer.length);
          if (bytes >= 0) {
            byte[] pcm = new byte[bytes];
            System.arraycopy(buffer, 0, pcm, 0, bytes);
            line.write(buffer, 0, bytes);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      closeLine();
      closeAudioInputStream();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    }
  }

  private void openLine() throws LineUnavailableException {
    if (line != null) {
      int bufferSize = line.getBufferSize();
      line.open(decodedAudioFormat, bufferSize);
      line.start();
    }
  }

  private void closeLine() {
    line.drain();
    line.stop();
    line.close();
  }

  private void closeAudioInputStream() {
    try {
      if (decodedAudioInputStream != null) {
        decodedAudioInputStream.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
