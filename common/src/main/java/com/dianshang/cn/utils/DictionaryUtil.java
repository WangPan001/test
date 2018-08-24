package com.dianshang.cn.utils;

public final class DictionaryUtil {

	public enum ReturnCodeEnum {
		CODE_SUCCESS("00100000","操作成功"),
		CODE_FAIL("00100001","操作失败");
		
		
		private String value;

		private String label;

		private ReturnCodeEnum(String value, String label) {
			this.value = value;
			this.label = label;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
}
