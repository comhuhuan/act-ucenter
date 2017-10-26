package com.act.ucenter.common.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: TEC 3rd of CNNIC </p>
 * @author Orsen Leo
 * @version 1.0
 */

public class PunyException extends Exception implements java.io.Serializable{

  public PunyException() {
    super();
  }

  public PunyException(String msg) {
        super(msg);
  }
}