package Network.Requests;

public class UpdateStateRequest {
	
	private int index;
	
	public UpdateStateRequest(){
		index = -1;
	}
	
	public UpdateStateRequest(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}
