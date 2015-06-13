/* -*- mode: java; c-basic-offset: 2; indent-tabs-mode: nil -*- */

/*
  Copyright (c) The Processing Foundation 2015
  Developed by Gottfried Haider as part of GSOC 2015

  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General
  Public License along with this library; if not, write to the
  Free Software Foundation, Inc., 59 Temple Place, Suite 330,
  Boston, MA  02111-1307  USA
*/

package processing.io;

import processing.core.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GPIO {

  public static final int INPUT = 0;
  public static final int OUTPUT = 1;
  public static final int INPUT_PULLUP = 2;
  public static final int INPUT_PULLDOWN = 3;

  public static final int LOW = 0;
  public static final int HIGH = 1;

  public static void pinMode(int pin, int mode) {
    try {
      PrintWriter out = new PrintWriter("/sys/class/gpio/export");
      out.printf("%d", pin);
      out.close();
    } catch (FileNotFoundException e) {
      System.err.println("Make sure that your kernel is compiled with GPIO_SYSFS");
    } catch (Exception e) {
      System.out.println(e);
    }

    // delay to give udev a chance to change the file permissions behind our back
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    String fn = String.format("/sys/class/gpio/gpio%d/direction", pin);
    try {
      PrintWriter out = new PrintWriter(fn, "UTF-8");
      if (mode == INPUT) {
        out.write("in");
      } else if (mode == OUTPUT) {
        out.write("out");
      } else if (mode == INPUT_PULLUP) {
        // see http://marc.info/?l=linux-gpio&m=143431107502960&w=4
      } else if (mode == INPUT_PULLDOWN) {
      } else {
        // exception
      }
      out.close();
    } catch (FileNotFoundException e) {
      System.err.println("Make sure that your user has permissions to modify "+fn);
    } catch (Exception e) {
      System.out.println(e);
    }

    // pin = labeled pins, not GPIO pins?
    // throws exception
    // not everything is input per default
    // make it possible to do digitalWrite(x, HIGH), pinMode(x, OUTPUT)
  }

  public static void releasePin(int pin) {
    try {
      PrintWriter out = new PrintWriter("/sys/class/gpio/unexport");
      out.printf("%d", pin);
      out.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static void digitalWrite(int pin, int value) {
    try {
      String fn = String.format("/sys/class/gpio/gpio%d/value", pin);
      PrintWriter out = new PrintWriter(fn, "UTF-8");
      if (value == LOW) {
        out.write("0");
      } else if (value == HIGH) {
        out.write("1");
      } else {
        // exception
      }
      out.close();
    } catch (Exception e) {
      System.out.println(e);
    }

    // pin = labeled pins, not GPIO pins?
    // value = HIGH (1), LOW (0); int vs byte?
    // don't emulate Arduino behavior for setting pullup via digitalWrite?
    // throws exception
  }

  public static void digitalWrite(int pin, boolean value) {
    if (value) {
      digitalWrite(pin, HIGH);
    } else {
      digitalWrite(pin, LOW);
    }
  }

  public static int digitalRead(int pin) {
    try {
      String fn = String.format("/sys/class/gpio/gpio%d/value", pin);
      Path path = Paths.get(fn);
      String value = new String(Files.readAllBytes(path));
      return Integer.parseInt(value.substring(0, 1));
    } catch (Exception e) {
      System.out.println(e);
      return 0;
    }

    // pin = labeled pins, not GPIO pins?
    // returns HIGH (1), LOW (0)?
    // throws exception
  }

  public static void analogWrite(int pin, int value) {
  }

  // also: SPI, I2C (Wire), Servo?, IRQ
}
