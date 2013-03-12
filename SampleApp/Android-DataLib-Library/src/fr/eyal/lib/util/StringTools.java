/*
 * Copyright (C) 2012 Eyal LEZMY (http://www.eyal.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.eyal.lib.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

/**
 * @author Eyal LEZMY
 */
public class StringTools {

    /**
     * Get the IP address to string, from the integer form of it
     * 
     * @param ip the IP on an integer form
     * 
     * @return the IP address with the form XXX.XXX.XXX.XXX
     */
    public static String getIpAddressToString(final int ip) {

        final int mask = 0xFF;
        final int ip1 = ip & mask;
        final int ip2 = (ip >> 8) & mask;
        final int ip3 = (ip >> 16) & mask;
        final int ip4 = (ip >> 24) & mask;

        return ip1 + "." + ip2 + "." + ip3 + "." + ip4;
    }

    /**
     * Get the content of a file stored in a String.
     * 
     * @param context the Context of application
     * @param dirPath the path to go through starting for the root of the external storage
     * @param fileName the name of the file to open
     * 
     * @return the content of the expected file, include in a {@link String}
     * 
     * @throws IOException in case of error duting the {@link BufferedInputStream} work
     * @throws IllegalAccessException if the function is ot able to access to the storage
     */
    public static String getStringFromExternalFile(final Context context, String dirPath, final String fileName) throws IOException, IllegalAccessException {

        final String state = Environment.getExternalStorageState();

        // If we can read the SDCard
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

            //we get the XML File
            final File rootDir = context.getExternalFilesDir(null);
            dirPath = rootDir.getAbsolutePath() + File.separatorChar + dirPath;
            final File catalogFile = new File(dirPath, fileName);

            //we get the XML as a String
            final byte[] buffer = new byte[(int) catalogFile.length()];
            BufferedInputStream in = null;
            try {
                in = new BufferedInputStream(new FileInputStream(catalogFile));
                in.read(buffer);

            } finally {
                if (in != null)
                    try {
                        in.close();
                    } catch (final IOException ignored) {
                    }
            }
            final String xml = new String(buffer);
            return xml;
        } else {
            throw new IllegalAccessException();
        }
    }

}
