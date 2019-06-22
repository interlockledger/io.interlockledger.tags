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
 * This class implements the ILInt ILTag.
 *
 * @author Fabio Jun Takada Chino
 * @since 2019.06.10
 */
public class ILILIntTag extends ILTag {

	protected long value;

	public ILILIntTag(long id) {
		super(id);
	}
	
	public ILILIntTag() {
		super(ILStandardTags.TAG_ILINT64.ordinal());
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {
		out.writeILInt(this.getValue());
	}

	@Override
	public long getValueSize() {
		return ILIntCodec.getEncodedSize(this.value);
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in) throws ILTagException {

		if (tagSize != -1) {
			throw new ILTagException("Invalid value.");
		}
		this.value = in.readILInt();
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
	
	@Override
	protected boolean sameValue(ILTag other) {
		ILILIntTag t = (ILILIntTag)other;
		return this.getValue() == t.getValue();
	}
}
