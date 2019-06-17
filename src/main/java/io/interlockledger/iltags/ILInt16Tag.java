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

import io.interlockledger.iltags.io.ILTagDataReader;
import io.interlockledger.iltags.io.ILTagDataWriter;

/**
 * This class implements the standard int16 tag and its variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.12
 */
public class ILInt16Tag extends ILFixedSizeTag {
	
	protected short value;

	public ILInt16Tag(long id) {
		super(id, 2);
	}
	
	public ILInt16Tag() {
		this(ILTagStandardTags.TAG_INT16);
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException{
		out.writeShort(this.value);
	}

	@Override
	protected void deserializeValueCore(ILTagFactory factory, ILTagDataReader in)
			throws ILTagException {
		int read = in.readShort();
		this.value = (byte)read;
	}

	public short getValue() {
		return value;
	}

	public void setValue(short value) {
		this.value = value;
	}
}
