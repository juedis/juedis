package com.taobao.juedis.packet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.taobao.juedis.core.BinaryJudis.Command;

/**
 * 
 * @Description:
 * 
 * @author ÇïÄê
 * 
 * @date 2011-8-24 ÏÂÎç03:27:14
 * 
 */
public class JudisRequest {

	private List<byte[]> objs = new ArrayList<byte[]>();

	private Command cmd;

	private ArrayBlockingQueue<JudisResponse> queue;

	public ArrayBlockingQueue<JudisResponse> getQueue() {
		return queue;
	}

	public void setQueue(ArrayBlockingQueue<JudisResponse> queue) {
		this.queue = queue;
	}

	public JudisRequest(Command cmd) {
		this.cmd = cmd;
	}

	public Command getCmd() {
		return cmd;
	}

	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}

	public List<byte[]> getObjs() {
		return objs;
	}

	public void setObjs(List<byte[]> objs) {
		this.objs = objs;
	}

	public void addObj(byte[] b) {
		objs.add(b);
	}

}
