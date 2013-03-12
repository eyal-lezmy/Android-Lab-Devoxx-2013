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

import android.util.Log;
import fr.eyal.lib.data.config.DataLibConfig;

/**
 * @author Eyal LEZMY
 */
public class Out {

    public static void d(final String tag, final String msg) {
        if (DataLibConfig.SHOW_LOG)
            Log.d(tag, msg);
    }

    public static void e(final String tag, final String msg) {
        if (DataLibConfig.SHOW_LOG)
            Log.e(tag, msg);
    }

    public static void i(final String tag, final String msg) {
        if (DataLibConfig.SHOW_LOG)
            Log.i(tag, msg);
    }

    public static void v(final String tag, final String msg) {
        if (DataLibConfig.SHOW_LOG)
            Log.v(tag, msg);
    }

    public static void w(final String tag, final String msg) {
        if (DataLibConfig.SHOW_LOG)
            Log.w(tag, msg);
    }

    public static void wtf(final String tag, final String msg) {
        if (DataLibConfig.SHOW_LOG)
            Log.wtf(tag, msg);
    }

    public static void d(final String tag, final String msg, final Throwable tr) {
        if (DataLibConfig.SHOW_LOG)
            Log.d(tag, msg, tr);
    }

    public static void e(final String tag, final String msg, final Throwable tr) {
        if (DataLibConfig.SHOW_LOG)
            Log.e(tag, msg, tr);
    }

    public static void i(final String tag, final String msg, final Throwable tr) {
        if (DataLibConfig.SHOW_LOG)
            Log.i(tag, msg, tr);
    }

    public static void v(final String tag, final String msg, final Throwable tr) {
        if (DataLibConfig.SHOW_LOG)
            Log.v(tag, msg, tr);
    }

    public static void w(final String tag, final String msg, final Throwable tr) {
        if (DataLibConfig.SHOW_LOG)
            Log.w(tag, msg, tr);
    }

    public static void wtf(final String tag, final String msg, final Throwable tr) {
        if (DataLibConfig.SHOW_LOG)
            Log.wtf(tag, msg, tr);
    }

}
