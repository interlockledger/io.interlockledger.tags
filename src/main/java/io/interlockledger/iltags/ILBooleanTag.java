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
 * This class implements the boolean ILTag.
 *
 * @author Fabio Jun Takada Chino
 * @since 2019.06.10
 */
public class ILBooleanTag extends ILFixedSizeTag {

	public boolean value;

	public ILBooleanTag() {
		this(ILStandardTags.TAG_BOOL);
	}

	public ILBooleanTag(long id) {
		super(id, 1);
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {
		out.writeByte((byte) (this.value ? 1 : 0));
	}

	@Override
	protected void deserializeValueCore(ILTagFactory factory, ILTagDataReader in) throws ILTagException {
		switch (in.readByte()) {
		case 0:
			this.value = false;
			break;
		case 1:
			this.value = true;
			break;
		default:
			throw new ILTagException("Invalid value.");
		}
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
}
