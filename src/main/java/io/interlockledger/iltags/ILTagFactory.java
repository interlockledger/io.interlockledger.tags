/*
 * Copyright 2019 InterlockLedger Network
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
package io.interlockledger.iltags;

import java.io.InputStream;

public class ILTagFactory {

	private boolean strictMode;
	
	public ILTag create(long tagId) throws ILTagException {
		return null;
	}

	public boolean isStrictMode() {
		return strictMode;
	}
	
	/**
	 * Deserializes the first ILTag found inside the input.
	 *
	 * @param[in] The input buffer.
	 * @return The extracted tag or NULL otherwise.
	 */
	public ILTag deserialize(InputStream in) throws ILTagException {
		return null;
	}
	
}
