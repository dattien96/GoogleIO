/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dattien96.vn.model

/**
 * Describes a venue associated with the conference.
 */
data class Room(
    /**
     * Unique string identifying this room.
     */
    val id: String,

    /**
     * The name of the room.
     */
    val name: String
) {
    val abbreviatedName
        get() = name.split("|")[0]
}