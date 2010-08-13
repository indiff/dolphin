//import java.applet.Applet;
//import java.awt.BorderLayout;
//
//public class SimpleShadow extends Applet {
//	//三角平面类
//	public class TriPlane extends Shape3D {
//		TriPlane(Point3f A, Point3f B, Point3f C) {
//			this.setGeometry(this.createGeometry3(A, B, C));
//			this.setAppearance(this.createAppearance());
//		} //建立三角平面   
//
//		Geometry createGeometry3(Point3f A, Point3f B, Point3f C) {
//			TriangleArray plane = new TriangleArray(3,
//					GeometryArray.COORDINATES | GeometryArray.NORMALS);
//			//设置平面3个顶点的坐标  
//			plane.setCoordinate(0, A);
//			plane.setCoordinate(1, B);
//			plane.setCoordinate(2, C);
//			//计算平面法向量   
//			Vector3f a = new Vector3f(A.x - B.x, A.y - B.y, A.z - B.z);
//			Vector3f b = new Vector3f(C.x - B.x, C.y - B.y, C.z - B.z);
//			Vector3f n = new Vector3f();
//			n.cross(b, a);
//			//法向量单位化   
//			n.normalize();
//			//设置平面3个顶点的法向量   
//			plane.setNormal(0, n);
//			plane.setNormal(1, n);
//			plane.setNormal(2, n);
//			return plane;
//		} //创建Material不为空的外观  
//
//		Appearance createAppearance() {
//			Appearance appear = new Appearance();
//			Material material = new Material();
//			appear.setMaterial(material);
//			return appear;
//		}
//	} //四边平面类
//
//	public class QuadPlane extends Shape3D {
//		QuadPlane(Point3f A, Point3f B, Point3f C, Point3f D) {
//			this.setGeometry(this.createGeometry4(A, B, C, D));
//			this.setAppearance(this.createAppearance());
//		} //创建四边性平面 
//
//		Geometry createGeometry4(Point3f A, Point3f B, Point3f C, Point3f D) {
//			QuadArray plane = new QuadArray(4, GeometryArray.COORDINATES
//					| GeometryArray.NORMALS);
//			//设置平面3个顶点的坐标  
//			plane.setCoordinate(0, A);
//			plane.setCoordinate(1, B);
//			plane.setCoordinate(2, C);
//			plane.setCoordinate(3, D);
//			//计算平面法向量   
//			Vector3f a = new Vector3f(A.x - B.x, A.y - B.y, A.z - B.z);
//			Vector3f b = new Vector3f(C.x - B.x, C.y - B.y, C.z - B.z);
//			Vector3f n = new Vector3f();
//			n.cross(b, a);
//			//法向量单位化  
//			n.normalize();
//			//设置平面4个顶点的法向量  
//			plane.setNormal(0, n);
//			plane.setNormal(1, n);
//			plane.setNormal(2, n);
//			plane.setNormal(3, n);
//			return plane;
//		}//创建Material不为空的外观  
//
//		Appearance createAppearance() {
//			Appearance appear = new Appearance();
//			Material material = new Material();
//			appear.setMaterial(material);
//			return appear;
//		}
//	} //阴影类 
//
//	public class Shadow3D extends Shape3D {
//		Shadow3D(GeometryArray geom, Vector3f direction, Color3f col,
//				float height) {
//			int vCount = geom.getVertexCount();
//			TriangleArray poly = new TriangleArray(vCount,
//					GeometryArray.COORDINATES | GeometryArray.COLOR_3);
//			int v;
//			Point3f vertex = new Point3f();
//			Point3f shadow = new Point3f();
//			for (v = 0; v < vCount; v++) {
//				//计算可见面顶点在投影面上的投影坐标  
//				geom.getCoordinate(v, vertex);
//				System.out.println(vertex.y - height);
//				shadow.set(vertex.x - (vertex.y - height), height + 0.0001f,
//						vertex.z - (vertex.y - height));
//				poly.setCoordinate(v, shadow);
//				poly.setColor(v, col);
//			}
//			this.setGeometry(poly);
//		}
//	}
//
//	public SimpleShadow() {
//		this.setLayout(new BorderLayout());
//		//GraphicsConfiguration config =SimpleUniverse.getPreferredConfiguration();
//		Canvas3D c = new Canvas3D(null);
//		this.add("Center", c);
//		//创建分支节点  
//		BranchGroup scene = new BranchGroup();
//		//创建三角形可见平面 
//		Shape3D plane = new TriPlane(new Point3f(0.0f, 0.6f, -0.2f),
//				new Point3f(-0.3f, -0.3f, 0.2f), new Point3f(0.6f, -0.3f, 0.2f));
//		//添加到根分支节点 
//		scene.addChild(plane);
//		//创建四边形投影平面 
//		Shape3D floor = new QuadPlane(new Point3f(-1.0f, -0.5f, -1.0f),
//				new Point3f(-1.0f, -0.5f, 1.0f),
//				new Point3f(1.0f, -0.5f, 1.0f), new Point3f(1.0f, -0.5f, -1.0f));
//		//添加到根分支节点 
//		scene.addChild(floor);
//		//添加到环境光  
//		AmbientLight lightA = new AmbientLight();
//		lightA.setInfluencingBounds(new BoundingSphere());
//		scene.addChild(lightA);
//		//添加平行光  
//		DirectionalLight lightD1 = new DirectionalLight();
//		lightD1.setInfluencingBounds(new BoundingSphere());
//		Vector3f direction = new Vector3f(-1.0f, -1.0f, -1.0f);
//		//方向矢量单位化   
//		direction.normalize();
//		lightD1.setDirection(direction);
//		scene.addChild(lightD1);
//		//创建阴影物体  
//		Shape3D shadow = new Shadow3D((GeometryArray) plane.getGeometry(),
//				direction, new Color3f(0.2f, 0.2f, 0.2f), -0.5f);
//		scene.addChild(shadow);
//		SimpleUniverse u = new SimpleUniverse(c);
//		u.getViewingPlatform().setNominalViewingTransform();
//		u.addBranchGraph(scene);
//	}
//
//	public static void main(String argv[]) {
//		new MainFrame(new SimpleShadow(), 256, 256);
//	}
//}
