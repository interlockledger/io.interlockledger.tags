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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ILTestTag extends ILTag {

	private long valueSize;
	
	public ILTestTag(long id, long valueSize) {
		super(id);
		this.valueSize = valueSize;
	}

	@Override
	protected void serializeValue(DataOutputStream out) throws ILTagException, IOException {
		
		for (long i = 0; i < this.valueSize; i++) {
			out.write((int)(i & 0xFF));
		}
	}

	@Override
	public long getValueSize() {
		return this.valueSize;
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, DataInputStream in)
			throws ILTagException, IOException {
		throw new UnsupportedOperationException("This method should not be tested.");
	}
}
