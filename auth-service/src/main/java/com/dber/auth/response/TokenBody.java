package com.dber.auth.response;

import lombok.Data;

@Data
public class TokenBody {
  private int code;
  private long userId;
  private String userName;
}
