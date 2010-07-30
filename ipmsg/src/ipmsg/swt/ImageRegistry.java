package ipmsg.swt;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.graphics.Image;

/**
 * �C���[�W�̈ꌳ�Ǘ��������Ȃ��N���X�B
 */
public final class ImageRegistry {
	private static HashMap<String, Image> images = 
		new HashMap<String, Image>();
	
	/** �C���[�W���擾���܂��B*/
	public final static synchronized Image getImage(final String filename){
		if(images.get(filename)!=null){
			return (Image)images.get(filename);
		}
		Image image = new Image(null,filename);
		images.put(filename,image);
		return image;
	}
	
	/** ���\�[�X��������܂��B*/
	public final static synchronized void dispose(){
		Iterator<Image> ite = images.values().iterator();
		while(ite.hasNext()){
			Image image = ite.next();
			image.dispose();
		}
	}
}
