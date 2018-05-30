package com.dber.auth.response;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;

public class WrapperOutputStream extends ServletOutputStream {

  private ByteArrayOutputStream outputStream;

  public WrapperOutputStream(ByteArrayOutputStream outputStream) {
    this.outputStream = outputStream;
  }

  @Override
  public boolean isReady() {
    return false;
  }

  @Override
  public void setWriteListener(WriteListener listener) {

  }

  @Override
  public void write(int b) {
    outputStream.write(b);
  }
}
