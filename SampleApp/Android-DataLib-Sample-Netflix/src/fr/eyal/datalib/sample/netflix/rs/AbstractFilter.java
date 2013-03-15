/*
*   Copyright 2012 - Genymobile
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/

package fr.eyal.datalib.sample.netflix.rs;

import android.graphics.Bitmap;

public abstract class AbstractFilter {

    // Matrice du filtre a appliquer (initialisée en matrice identité)
    protected float[] mMatrix = {
            1, 0, 0,
            0, 1, 0,
            0, 0, 1
    };

    public void setMatrix(float[] matrix) {
        mMatrix = matrix;
    }

    // Abstract
    public abstract void applyFilter(Bitmap inputBitmap, Bitmap outputBitmap);
}
