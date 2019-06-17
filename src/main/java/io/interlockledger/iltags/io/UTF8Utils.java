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
package io.interlockledger.iltags.io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

/**
 * UTF-8 utility methods.
 *
 * @author Fabio Jun Takada Chino
 * @since 2019.06.17
 */
public class UTF8Utils {

	/**
	 * The UTF-8 charset.
	 */
	public static final Charset UTF8 = Charset.forName("utf-8");

	/**
	 * Creates a new UTF-8 decoder set to be as strict as possible.
	 * 
	 * @return The new decoder.
	 */
	public static CharsetDecoder newDecoder() {
		return UTF8.newDecoder()
				.onMalformedInput(CodingErrorAction.REPORT)
				.onUnmappableCharacter(CodingErrorAction.REPORT);
	}
	
	/**
	 * Creates a new UTF-8 encoder set to be as strict as possible.
	 * 
	 * @return The new encoder.
	 */
	public static CharsetEncoder newEncoder() {
		return UTF8.newEncoder()
				.onMalformedInput(CodingErrorAction.REPORT)
				.onUnmappableCharacter(CodingErrorAction.REPORT);
	}
	
	/**
	 * Computes the encoded size of the string s.
	 * 
	 * @param s The string.
	 * @return The encoded size in bytes.
	 */
	public static int getEncodedSize(CharSequence s) {
		// TODO Improve this method later
		try {
			ByteBuffer b = newEncoder().encode(CharBuffer.wrap(s));
			return b.limit();
		} catch (CharacterCodingException e) {
			throw new IllegalArgumentException("Invalid string.", e);
		}
	}
}
