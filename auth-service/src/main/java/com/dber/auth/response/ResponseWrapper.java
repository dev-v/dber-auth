package com.dber.auth.response;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseWrapper extends HttpServletResponseWrapper {
  private ByteArrayOutputStream outputStream;
  private ServletOutputStream servletOutputStream;

  public ResponseWrapper(HttpServletResponse response) {
    super(response);
    outputStream = new ByteArrayOutputStream();
    servletOutputStream = new WrapperOutputStream(outputStream);
  }


  @Override
  public ServletOutputStream getOutputStream() {
    return servletOutputStream;
  }

  @Override
  public void flushBuffer() throws IOException {
    servletOutputStream.flush();
  }

  public byte[] getContent() throws IOException {
    flushBuffer();
    return outputStream.toByteArray();
  }
}
