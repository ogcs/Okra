/**
 *
 * murmurhash - Pure Java implementation of the Murmur Hash algorithms.
 * Copyright (c) 2014, Sandeep Gupta
 * 
 * http://sangupta.com/projects/murmur
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.ogcs.utilities.math.murmur;

/**
 * Simple constants for various masks to convert to unsigned values.
 * 
 * @author sangupta
 *
 */
public interface MurmurConstants {

	/**
	 * Default seed
	 */
	int DEFAULT_SEED = 0xB0F57EE3;

	/**
	 * Helps convert a byte into its unsigned value
	 */
	public static final int UNSIGNED_MASK = 0xff;

	/**
	 * Helps convert integer to its unsigned value
	 */
	public static final long UINT_MASK = 0xFFFFFFFFl;
	
	/**
	 * Helps convert long to its unsigned value
	 */
	public static final long LONG_MASK = 0xFFFFFFFFFFFFFFFFL;

}
