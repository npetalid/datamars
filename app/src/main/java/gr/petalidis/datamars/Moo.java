/*        Copyright 2019 Nikolaos Petalidis
 *
 *        Licensed under the Apache License, Version 2.0 (the "License");
 *        you may not use this file except in compliance with the License.
 *        You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing, software
 *        distributed under the License is distributed on an "AS IS" BASIS,
 *        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *        See the License for the specific language governing permissions and
 *        limitations under the License.
 */
package gr.petalidis.datamars;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Moo extends Application {

    private static WeakReference<Context> weakReference;
    final static private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public void onCreate() {
        super.onCreate();
        Moo.weakReference = new WeakReference<>(getApplicationContext());
    }

    public static Context getAppContext()
    {
        Context context = Objects.requireNonNull(weakReference.get());
        return context;
    }

    public static SimpleDateFormat getFormatter() {
        return formatter;
    }
}
