package com.poscodx.mysite.dto;

public class JsonResult {

	 private String Result; // "success" or "fail" 
	 private String message; // if fail, set 
	 private Object data;  //  if success, set 
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "JsonResult [Result=" + Result + ", message=" + message + ", data=" + data + "]";
	}
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
