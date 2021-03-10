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

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Base64;
import java.util.Random;

public abstract class TestUtils {

	public static final Charset UTF8 = Charset.forName("utf8");

	/**
	 * Sample extracted from "A Mulher de Preto" by Machado de Assis. This one was
	 * used because it contains multiple accents.
	 */
	public static final String SAMPLE = "Estêvão Soares teve de ir à casa de um ministro de Estado para saber"
			+ " deuns papéis relativos a um parente da província, e aí encontrou o"
			+ " deputado Meneses, que acabava de ter uma conferência política.\n";

	/**
	 * The value of SAMPLE encoded in UTF-8.
	 */
	public static final byte[] SAMPLE_BIN = Base64.getDecoder()
			.decode("RXN0w6p2w6NvIFNvYXJlcyB0ZXZlIGRlIGlyIMOgIGNhc2EgZGUgdW0gbWluaXN0cm8gZGUgRXN0"
					+ "YWRvIHBhcmEgc2FiZXIgZGV1bnMgcGFww6lpcyByZWxhdGl2b3MgYSB1bSBwYXJlbnRlIGRhIHBy"
					+ "b3bDrW5jaWEsIGUgYcOtIGVuY29udHJvdSBvIGRlcHV0YWRvIE1lbmVzZXMsIHF1ZSBhY2FiYXZh"
					+ "IGRlIHRlciB1bWEgY29uZmVyw6puY2lhIHBvbMOtdGljYS4K");

	public static byte[] createSampleByteArray(int size) {
		byte[] v = new byte[size];
		fillSampleByteArray(v, 0, v.length);
		return v;
	}

	public static void fillSampleByteArray(byte[] v, int offs, int size) {

		for (int i = 0; i < size; i++) {
			v[i + offs] = (byte) (i & 0xFF);
		}
	}

	/**
	 * Generates a random string using only single character codepoints.
	 * 
	 * @param size The number of characters.
	 * @return The generated string.
	 */
	public static String genRandomString(int size) {
		return genRandomString(size, false);
	}

	/**
	 * Generates a random string.
	 * 
	 * <p>
	 * If all is set to true, all possible codepoints from U+0 to U+10FFFF are used
	 * with the exception of the reserved range U+D800 to U+DFFF. If all is set to
	 * false, only characters from U+0 to U+FFFF will be used, excluding the
	 * reserved range.
	 * </p>
	 * 
	 * <p>
	 * The result will contain exactly the specified number of characters but may
	 * contain less codepoints due to the use of surrogate pairs.
	 * </p>
	 * 
	 * @param size The number of characters.
	 * @param all  If true, include single and double character codepoints otherwise
	 *             uses only single character codepoints.
	 * @return The generated string.
	 */
	public static String genRandomString(int size, boolean all) {
		Random random = new Random();
		char[] tmp = new char[2];
		int range = all ? 0x10FFFF : 0xFFFF;

		StringBuffer sb = new StringBuffer(size);
		while (size > 0) {
			int cp = random.nextInt(range) + 1;
			if ((cp < 0xD800) || (cp > 0xDFFF)) {
				int len = Character.toChars(cp, tmp, 0);
				if (len == 1) {
					size--;
					sb.append(tmp[0]);
				} else {
					if (size > 1) {
						size -= 2;
						sb.append(tmp, 0, 2);
					}
				}
			}
		}
		return sb.toString();
	}

	public static byte[] stringToUTF8(String s) throws Exception {

		ByteBuffer b = UTF8.newEncoder().onMalformedInput(CodingErrorAction.REPORT)
				.onUnmappableCharacter(CodingErrorAction.REPORT).encode(CharBuffer.wrap(s));
		byte[] ret = new byte[b.limit()];
		b.get(ret);
		return ret;
	}
}
