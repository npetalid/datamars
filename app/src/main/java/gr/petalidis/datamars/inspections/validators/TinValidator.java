
/*
 * Copyright 2017-2019 Nikolaos Petalidis
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package gr.petalidis.datamars.inspections.validators;

import java.util.stream.IntStream;

public class TinValidator implements Validator<String> {
    @Override
    public boolean isValid(String tin) {

        if (tin.length() < 9) {
            return false;
        }
        Double iSum = IntStream.range(0, tin.length() - 1).mapToDouble(i -> Integer.parseInt(tin.substring(i, i + 1))
                * (int) Math.pow(2, (tin.length() - i - 1))).sum();
        if (iSum == 0) {
            return false;
        }

        Double btRem = iSum % 11;

        int lastDigit = Integer.parseInt(tin.substring(tin.length() - 1));
        return lastDigit == btRem || (btRem == 10 && lastDigit == 0);
    }
}
