package ChatClient;

//: com:bruceeckel:swing:WClose.java
// From 'Thinking in Java, 2nd Ed.' by Bruce Eckel
// www.BruceEckel.com. See copyright notice in CopyRight.txt.
//package com.bruceeckel.swing;
import java.awt.event.*;
public class WClose extends WindowAdapter {
  public void windowClosing(WindowEvent e) {
    System.exit(0);
  }
} ///:~