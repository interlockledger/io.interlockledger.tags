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

public interface ILTagStandardTags {
	/**
	 * The NULL tag. It contains no payload.
	 */
	public static final long TAG_NULL = 0;
	/**
	 * The boolean tag. It contains a single byte that represents true (1)
	 * or false (0).
	 */
	public static final long TAG_BOOL = 1;
	/**
	 * The signed 8-bit integer tag. It contains a single byte that
	 * represents the value.
	 */
	public static final long TAG_INT8 = 2;
	/**
	 * The unsigned 8-bit integer tag. It contains a single byte that
	 * represents the value.
	 */
	public static final long TAG_UINT8 = 3;
	/**
	 * The signed 16-bit integer tag. It contains 2 bytes that represents
	 * the value in Big Endian.
	 */
	public static final long TAG_INT16 = 4;
	/**
	 * The unsigned 16-bit integer tag. It contains 2 bytes that represents
	 * the value in Big Endian.
	 */
	public static final long TAG_UINT16 = 5;
	/**
	 * The signed 32-bit integer tag. It contains 4 bytes that represents
	 * the value in Big Endian.
	 */
	public static final long TAG_INT32 = 6;
	/**
	 * The unsigned 32-bit integer tag. It contains 4 bytes that represents
	 * the value in Big Endian.
	 */
	public static final long TAG_UINT32 = 7;
	/**
	 * The signed 64-bit integer tag. It contains 8 bytes that represents
	 * the value in Big Endian.
	 */
	public static final long TAG_INT64 = 8;
	/**
	 * The unsigned 64-bit integer tag. It contains 8 bytes that
	 * represents the value in Big Endian.
	 */
	public static final long TAG_UINT64 = 9;
	/**
	 * The ILInt64 tag. It contains 1 to 9 bytes that represents a 64-bit
	 * integer encoded as an ILInt.
	 */
	public static final long TAG_ILINT64 = 10;
	/**
	 * The single precision floating point tag. It contains a 4 bytes that
	 * represents the value encoded as IEEE-754 in Big Endian.
	 */
	public static final long TAG_BINARY32 = 11;
	/**
	 * The double precision floating point tag. It contains a 8 bytes that
	 * represents the value encoded as IEEE-754 in Big Endian.
	 */
	public static final long TAG_BINARY64 = 12;
	/**
	 * The quadruple precision floating point tag. It contains a 16 bytes
	 * that represents the value encoded as IEEE-754 in Big Endian. The
	 * support for this tag is optional.
	 */
	public static final long TAG_BINARY128 = 13;
	/**
	 * The byte array tag. It contains a byte array with 0 or more bytes.
	 */
	public static final long TAG_BYTE_ARRAY = 16;
	/**
	 * The string tag. It contains a UTF-8 string with 0 or more caracters.
	 */
	public static final long TAG_STRING = 17;
	/**
	 * The big integer tag. It contains the value encoded as
	 * two's complement big endian value.
	 */
	public static final long TAG_BINT = 18;
	/**
	 * The big decimal tag. It contains an arbitrary precision decimal
	 * encoded as an ILInt64 for the precision and the integral part as
	 * encoded as two's complement big endian value.
	 */
	public static final long TAG_BDEC = 19;
	/**
	 * The ILnt64 array tag. It contains an array of 64-bit integers encoded
	 * as ILInt64 values. It is composed by an ILInt64 that encodes the
	 * number of entries followed by the entries themselves.
	 */
	public static final long TAG_ILINT64_ARRAY = 20;
	/**
	 * The ILTag array tag. It contains an ILInt64 value that encodes the
	 * number of objects followed by the serialization of the tags.
	 */
	public static final long TAG_ILTAG_ARRAY = 21;
    /**
	 * The ILTag sequence tag. It contains the serialization of a sequence of tags.
	 */
	public static final long TAG_ILTAG_SEQ = 22;
	/**
	* The ILTag range tag. It contains a range of ILTagId.
	*/
	public static final long TAG_RANGE = 23;
	/**
	* The ILTag version tag. It contains the semantic version number (major.minor.revision.build).
	*/
	public static final long TAG_VERSION = 24;
	/**
	 * ITU Object Identifier. 
	 */
	public static final long TAG_OID = 25;
}
