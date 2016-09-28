/*
 *     Copyright 2016-2026 TinyZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ogcs.okra.example.game.business;

import java.util.Map;

/**
 * @author TinyZ
 * @date 2016-09-25.
 */
public class CfgBusTemplate {

    private int id;
    private Integer[] events;
    private Map<Integer, Integer> targets;
    private boolean isDaily;
    private boolean isProgressDb;

}
