#pragma version(1)
#pragma rs java_package_name(name.duzenko.farfaraway.renderscript)

rs_allocation src, dest;
int src_size;
float delta;
rs_script gScript;

float starRadius2sq, starMass;

typedef float3 wType;

void root(const wType *v_in, wType *v_out) {
	wType tmp = 0;
    for(int i = 0; i < src_size; i++) {
        wType* f3 = (wType*)rsGetElementAt(src, i);
	    wType diff = *f3 - *v_in;
	    float sq_dist = dot(diff, diff);
	    if(sq_dist==0) 
	      continue;           
	    wType norm = normalize(diff);   
	    if (sq_dist>=starRadius2sq)
	      tmp += (norm/sq_dist);
	    else
	      tmp += (norm*sq_dist);    
    }  
    *v_out = tmp*delta*starMass;               
}        

void filter() {
    rsForEach(gScript, src, dest, NULL);
}