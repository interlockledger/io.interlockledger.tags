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

import java.beans.Encoder;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.ilint.ILIntException;

/**
 * This class implements the String ILTag.
 *
 * @author Fabio Jun Takada Chino
 * @since 2019.06.10
 */
public class ILStringTag extends ILTag {

	private static final Charset CHARSET = Charset.forName("utf-8");
	
	public String value;
	
	public ILStringTag() {
		super(ILTagStandardTags.TAG_STRING);
	}

	@Override
	protected void serializeValue(DataOutputStream out) throws ILTagException, IOException {
		ByteBuffer tmp = CHARSET.encode(this.value);
		out.write(tmp.array(), 0, tmp.limit());
	}

	@Override
	public long getValueSize() {
		CharsetEncoder encoder = CHARSET.newEncoder();
		ByteBuffer output = ByteBuffer.allocate(256);
		CharBuffer input = CharBuffer.wrap(this.value);
		long size = 0;
		while (encoder.encode(input, output, true) == CoderResult.OVERFLOW) {
			size += output.position();
			output.rewind();
		}
		size += output.position();
		return size; 
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, DataInputStream in) throws ILTagException, IOException {

		if (tagSize > Integer.MAX_VALUE) {
			throw new ILTagException("String too long.");
		}
		ByteBuffer bytes = ByteBuffer.allocate((int)tagSize);

		
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
