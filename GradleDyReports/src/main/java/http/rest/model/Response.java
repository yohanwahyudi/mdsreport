package http.rest.model;

public class Response<T> {
	
	private String service;
    private String message;
    private T data;
    
    public Response() {
    	
    }
    
    public Response(String service, String message, T data) {
    	this.service = service;
    	this.message = message;
    	this.data = data;
    }
    
	public String getService() {
		return service;
	}
	public String getMessage() {
		return message;
	}
	public T getData() {
		return data;
	}
	public void setService(String service) {
		this.service = service;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setData(T data) {
		this.data = data;
	}

}
