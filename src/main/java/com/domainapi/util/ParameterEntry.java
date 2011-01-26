/**
 * ParameterEntry
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 * @copyright Copyright (C) 2011 by domainAPI.com - EuroDNS S.A. 
 * @version Revision: 1.0
 * 
 * For the full copyright and license information,please view the LICENSE file
 * that was distributed with this source code.
 */
package com.domainapi.util;

import java.util.Map.Entry;

/**
 * Reprensentation of a HTTP parameter
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 */
public class ParameterEntry<K, V> implements Entry<K, V> {

	private K key;
	private V value;

	public ParameterEntry(K k, V v) {
		key = k;
		value = v;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public V setValue(V v) {
		value = v;
		return value;
	}
}
