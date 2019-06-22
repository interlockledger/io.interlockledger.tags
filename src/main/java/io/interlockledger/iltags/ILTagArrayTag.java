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

import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.io.ILTagDataReader;
import io.interlockledger.iltags.io.ILTagDataWriter;

/**
 * This class implements the standard tag array tag but can also be
 * used to implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public class ILTagArrayTag extends ILTagSequenceTag {

	public ILTagArrayTag() {
		this(ILStandardTags.TAG_ILTAG_ARRAY);
	}
	
	public ILTagArrayTag(long id) {
		super(id);
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {
		out.writeILInt(this.value.size());
		super.serializeValue(out);
	}

	@Override
	public long getValueSize() {
		return ILIntCodec.getEncodedSize(this.value.size()) + super.getValueSize();
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in)
			throws ILTagException {
		long count;

		in.pushLimit(tagSize);
		count = in.readILInt();
		if (count > Integer.MAX_VALUE) {
			throw new ILTagException("Unsupported number of entries.");
		}
		this.value.clear();
		for (; count > 0; count--) {
			this.value.add(factory.deserialize(in));
		}		
		in.popLimit(true);
	}
}
