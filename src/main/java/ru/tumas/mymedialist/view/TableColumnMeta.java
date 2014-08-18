package ru.tumas.mymedialist.view;

public class TableColumnMeta {

	private final String key;
	private final Class<?> clazz;

	public TableColumnMeta(String key, Class<?> clazz) {
		this.key = key;
		this.clazz = clazz;
	}

	public String getKey() {
		return key;
	}

	public Class<?> getClazz() {
		return clazz;
	}
}
