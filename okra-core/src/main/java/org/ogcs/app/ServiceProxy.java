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
package org.ogcs.app;

/**
 * Single Service Interface Proxy.
 * <p>Use to warp the session callback interface.</p>
 * <pre>
 *     <lang desc="zh-cn">
 *         当项目开始变大庞大时, 定义的接口越来越多，管理{@link Command}数量众多的接口，会变的很不方便.
 *              同时, 也不利于功能模块划分和管理
 *
 *         在这种情况下, 想到利用Java的代理模式, 通过接口代理. 减少{@link Command}数量，
 *         按功能或模块划分和实现对应的功能.
 *     </lang>
 * </pre>
 *
 * @param <T> the special service interface.
 * @author TinyZ.
 * @version 2017.07.07
 * @see MultiServiceProxy
 * @since 2.0
 */
public interface ServiceProxy<T> {

    /**
     * Get the api callback proxy.
     *
     * @return the api callback proxy.
     */
    T proxy();
}
