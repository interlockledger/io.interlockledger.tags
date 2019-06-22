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
 * This class implements the standard null ILTag or any other variant.
 * 
 * <p>The standard null tag does not have any value and is serialized
 * to a single 0x00 byte. Variants of this class that use other codes will
 * be serialized</p>
 *
 * @author Fabio Jun Takada Chino
 * @since 2019.06.10
 */
public class ILNullTag extends ILTag {
	
	public static final ILNullTag NULL = new ILNullTag();

	public ILNullTag() {
		super(ILStandardTags.TAG_NULL);
	}
	
	public ILNullTag(long id) {
		super(id);
	}

	@Override
	protected final void serializeValue(ILTagDataWriter out) throws ILTagException {
	}
	
	@Override
	protected final boolean sameValue(ILTag other) {
		return true;
	}

	@Override
	public final long getValueSize() {
		return 0;
	}

	@Override
	public final void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in) throws ILTagException {
		if (tagSize != 0) {
			throw new ILTagException("");
		}
	}
}
