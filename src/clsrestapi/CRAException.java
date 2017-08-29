/*
 * Copyright 2017 Ken Lowrie.
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
package clsrestapi;

//TODO: Do I need this? Can't I just throw the existing exceptions instead of creating a new class?

/**
 *
 * @author Ken Lowrie
 */
public class CRAException extends Exception {
    public CRAException () {

    }

    public CRAException (String message) {
        super (message);
    }

    public CRAException (Throwable cause) {
        super (cause);
    }

    public CRAException (String message, Throwable cause) {
        super (message, cause);
    }
}
