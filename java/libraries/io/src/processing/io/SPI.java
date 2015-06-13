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

import java.io.File;
import java.util.ArrayList;

public class SPI {

  public SPI(String dev) {

  }

  public static String[] list() {
    ArrayList<String> devs = new ArrayList<String>();
    File dir = new File("/dev");
    File[] files = dir.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.getName().startsWith("spidev")) {
          devs.add(file.getName());
        }
      }
    }
    return devs.toArray(new String[devs.size()]);
  }
}
