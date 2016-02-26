package com.backyardbrains.drawing;

import javax.microedition.khronos.opengles.GL10;

public class BYBBarGraph {

	protected BYBMesh mesh;
	protected BYBMesh axisMesh;
	protected float minV, maxV, minH, maxH;
	protected int numDivsV, numDivsH;
	protected float x, y, w,h;
	protected boolean bAxisSet = false;
	float markerLength = 10;
	float margin = markerLength + 10;
	float l, r, t,b;
	
	public BYBBarGraph(float [] values, float x, float y, float w, float h, float[] color){
		float barWidth = (Math.abs(w) - margin)/values.length;
		mesh =  new BYBMesh(BYBMesh.TRIANGLES);
		l = (w<0)?x+w:x + margin;
		r = (w<0)?x:x+w;
		b = (h<0)?y:y+h - margin;
		t = (h<0)?y+h:y;
		//mesh.addRectangle(x, y, w, h, BYBColors.yellow);
		if(values.length > 0){values[0] = 1.0f;}
		for(int i = 0; i < values.length; i++){
			mesh.addRectangle(l+i*barWidth, b, barWidth, -(b-t) * values[i], color);
		}
		this.x = x; this.y = y; this.w = w; this.h = h;
		
	}
	public void setVerticalAxis(float min, float max, int numDivs){
		if(axisMesh == null){
			axisMesh  = new BYBMesh(BYBMesh.LINES);
		}
		minV = min; maxV = max; numDivsV = numDivs;

		float inc = (Math.abs(h) - margin)/numDivs;
		axisMesh.addLine(l, b, l, t, BYBColors.white);
		for(int i = 0; i < numDivs+1; i++){
			axisMesh.addLine(l, t+ inc*i, l-markerLength,t+ inc*i, BYBColors.white );
		}
		bAxisSet = true;
	}
	public void setHorizontalAxis(float min, float max, int numDivs){
		if(axisMesh == null){
			axisMesh  = new BYBMesh(BYBMesh.LINES);
		}
		float inc = (Math.abs(w) - margin)/numDivs;
		axisMesh.addLine(l, b, r, b, BYBColors.white);
		for(int i = 0; i < numDivs+1; i++){
			axisMesh.addLine(l+ inc*i, b, l+ inc*i,b+markerLength, BYBColors.white );
		}
		minH = min; maxH = max; numDivsH = numDivs;
		bAxisSet = true;
	}
	public void draw(GL10 gl){
		mesh.draw(gl);
		if(bAxisSet){
			axisMesh.draw(gl);
		}
		//
	}
	
	
}