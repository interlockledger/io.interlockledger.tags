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
	 * Computes the encoded size of the string s. This implementation does
	 * consider surrogate characters during the computation.
	 * 
	 * @param s The string.
	 * @return The encoded size in bytes.
	 * @throws IllegalArgumentException If the string cannot be converted.
	 */
	public static int getEncodedSize(CharSequence s) {
		
		if (s.length() == 0) {
			return 0;
		} else {
			try {
				CharBuffer src = CharBuffer.wrap(s);
				int size = 0;
				int cp;
				do {
					cp = nextCodepoint(src);
					if (cp != -1) {
						size += getUTF8CharSize(cp);
					}
				} while (cp >= 0);
				return size;
			} catch (CharacterCodingException e) {
				throw new IllegalArgumentException("This string contains illegal unicode characters.");
			}
		}
	}
	
	/**
	 * Returns the number of bytes required to encode a given codepoint in UTF-8.
	 * 
	 * @param cp The unicode codepoint.
	 * @return The number of bytes required to encode this codepoint.
	 * @since 2018.06.20
	 * @throws IllegalArgumentException In case of error.
	 */
	public static int getUTF8CharSize(int cp) {
		
		if (cp < 0) {
			throw new IllegalArgumentException("Invalid codepoint.");
		} else if (cp <= 0x7F) {
			return 1;
		} else if (cp <= 0x07FF) {
			return 2;
		} else if (cp <= 0xFFFF) {
			return 3;
		} else if (cp <= 0x10FFFF) {
			return 4;
		} else {
			throw new IllegalArgumentException("Invalid codepoint.");
		}
	}
	
	/**
	 * Returns the number of bytes required to encode a given codepoint in UTF-8 based
	 * on the first encoded byte.
	 * 
	 * @param firstByte The first byte
	 * @return The number of bytes required to encode this codepoint.
	 * @since 2018.06.20
	 * @throws IllegalArgumentException In case of error.
	 */
	public static int getUTF8EncodedCharSize(byte firstByte) {
		int v = firstByte & 0xFF;
		
		if (v <= 0b01111111) {
			return 1;
		} else if (v <= 0b11011111) {
			return 2;
		} else if (v <= 0b11101111) {
			return 3;
		} else if (v <= 0b11110111) {
			return 4;
		} else {
			throw new IllegalArgumentException("Invalid UTF8 character.");
		}
	}
	
	private static final int [] UTF8_1ST_CLEAR_MASK = {
			0b01111111,
			0b00011111,
			0b00001111,
			0b00000111};
	
	/**
	 * Converts up to 4 bytes into a codepoint using UTF-8 encoding. This method
	 * does not check the integrity of the first byte but will check the integrity
	 * of all subsequent bytes.  
	 * 
	 * @param b The bytes.
	 * @param size The size of the character. It must be a value between 1 and 4.
	 * @return The codepoint.
	 * @since 2018.06.20
	 * @throws IllegalArgumentException In case of error.
	 */
	public static int toCodepoint(byte b [], int size) {
		
		int cp = b[0] & UTF8_1ST_CLEAR_MASK[size - 1];
		for (int i = 1; i < size; i++) {
			int v = b[i] & 0xFF;
			if ((v & 0b11000000) != 0b10000000) {
				throw new IllegalArgumentException("Invalid encoded character.");
			}
			cp = (cp << 6) | (v & 0b00111111);
		}
		return cp;
	}
	
	private static final int [] UTF8_1ST_HEADER_MASK = {
			0b00000000,
			0b11000000,
			0b11100000,
			0b11110000};	
	
	/**
	 * Converts a unicode codepoint into its UTF-8 representation.
	 *  
	 * @param cp The codepoint.
	 * @param b The output byte array. It must have at least 4 bytes.
	 * @return The number of bytes used.
	 * @since 2018.06.20
	 * @throws IllegalArgumentException In case of error.
	 */
	public static int toUTF8(int cp, byte [] b) {
		int len = getUTF8CharSize(cp);

		// First
		b[0] = (byte)((cp >> (6 * (len - 1))) | UTF8_1ST_HEADER_MASK[len - 1]);
		for (int i = 1; i < len; i++) {
			b[i] = (byte)(((cp >> (6 * (len - i - 1)) & 0b00111111)) | 0b10000000);
		}
		return len;
	}
	
	/**
	 * Extracts a unicode codepoint from a CharBuffer.
	 * 
	 * @param src The source.
	 * @return The codepoint or -1 if it is the end of the data.
	 * @throws CharacterCodingException In case of an invalid codepoint.
	 */
	public static int nextCodepoint(CharBuffer src) throws CharacterCodingException {
		char high;
		char low;
		
		if (!src.hasRemaining()) {
			return -1;
		}
		high = src.get();
		if (Character.isHighSurrogate(high)) {
			if (src.hasRemaining()) {
				low = src.get();
				if (Character.isSurrogatePair(high, low)) {
					return Character.toCodePoint(high, low);
				} else {
					throw new CharacterCodingException();
				}
			} else {
				throw new CharacterCodingException();
			}
		} else {
			return high & 0xFFFF; 
		}
	}
}
