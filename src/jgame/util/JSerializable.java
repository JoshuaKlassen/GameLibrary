package jgame.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public abstract class JSerializable implements Serializable{

	private static final long serialVersionUID = -6114296254714031763L;

	protected abstract void init();
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject();
		init();
	}
	
}
