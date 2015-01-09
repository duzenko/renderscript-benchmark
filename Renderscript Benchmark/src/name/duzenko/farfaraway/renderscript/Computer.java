package name.duzenko.farfaraway.renderscript;

import name.duzenko.farfaraway.R;
import name.duzenko.farfaraway.simulation.Universe;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;

public class Computer {
	
	public Universe universe; 

	private RenderScript mRS;
	private Allocation mInAllocation;
	private Allocation mOutAllocation;
	private ScriptC_mono mScript;
	
	public Computer(Universe universe) {
		this.universe = universe;
	}

	public void createScript(Context context) {
		mRS = RenderScript.create(context);
		mInAllocation = Allocation.createSized(mRS, Element.F32_3(mRS), Universe.starsCount);
		mOutAllocation = Allocation.createTyped(mRS, mInAllocation.getType());
		mScript = new ScriptC_mono(mRS, context.getResources(), R.raw.mono);
		mScript.set_src_size(Universe.starsCount);
		mScript.set_starMass(Universe.starMass);
		mScript.set_starRadius2sq(4*Universe.starRadius*Universe.starRadius);
		mScript.set_src(mInAllocation);
		mScript.set_dest(mOutAllocation);
		mScript.set_gScript(mScript);
	}
	
	public void runScript(float []data, float delta) {
		//System.out.println("run script");
		mInAllocation.copyFrom(data);
		mScript.set_delta(delta);
		mScript.invoke_filter();//forEach_root(mInAllocation, mOutAllocation);
		mOutAllocation.copyTo(data);
	}

}
