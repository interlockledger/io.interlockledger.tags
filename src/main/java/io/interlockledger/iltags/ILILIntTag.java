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

import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.ilint.ILIntException;

/**
 * This class implements the ILInt ILTag.
 *
 * @author Fabio Jun Takada Chino
 * @since 2019.06.10
 */
public class ILILIntTag extends ILTag {

	public long value;
	
	public ILILIntTag() {
		super(ILTagStandardTags.TAG_ILINT64);
	}

	@Override
	protected void serializeValue(DataOutputStream out) throws ILTagException, IOException {
		try {
			ILIntCodec.encode(value, out);
		} catch (ILIntException e) {
			throw new ILTagException(e.getMessage(), e);
		}
	}

	@Override
	public long getValueSize() {
		return ILIntCodec.getEncodedSize(this.value);
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, DataInputStream in) throws ILTagException, IOException {

		if (tagSize != -1) {
			throw new ILTagException("Invalid value.");
		}		
		try {
			this.value = ILIntCodec.decode(in);
		} catch (ILIntException e) {
			throw new ILTagException(e.getMessage(), e);
		}
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
}
