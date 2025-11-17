// Name: Maadhyam Rawal
//
// Time Complexity and explanation: You can use the following variables for easier reference.
// n denotes the number of requests, p denotes the size of the cache
// n and p can be different and there is no assumption which one is larger
//
// evictFIFO():O(n×p)
// The algorithm iterates over each request in the request sequence, and for each request, it may traverse the cache, 
// which has a size proportional to the cache size p.
// evictLFD():O(n×p)
// Similar to evictFIFO, the algorithm iterates over each request in the request sequence, and for each request, it 
// may traverse the cache, which has a size proportional to the cache size p

class COMP108Paging {


	// evictFIFO
	// Aim: 
	// if a request is not in cache, evict the page present in cache for longest time
	// count number of hit and number of miss, and find the hit-miss pattern; return an object COMP108PagingOutput
	// Input:
	// cArray is an array containing the cache with cSize entries
	// rArray is an array containing the requeset sequence with rSize entries


/**
 * Implements the First-In-First-Out (FIFO) page replacement algorithm.
 * 
 * @param cArray The array containing the cache with cSize entries.
 * @param cSize The size of the cache.
 * @param rArray The array containing the request sequence with rSize entries.
 * @param rSize The size of the request sequence.
 * @return The paging output containing hit count, miss count, hit pattern, and updated cache.
 */

	static COMP108PagingOutput evictFIFO(int[] cArray, int cSize, int[] rArray, int rSize) {
		COMP108PagingOutput output = new COMP108PagingOutput();

		int lastIndex=0;
		boolean ifFound= false;

		// Initialise lastIndex and ifFound
		// lastIndex is set to 0 and ifFound to false 
		//Sample Inputs and Outputs[image-comments/image1.png]

		//Iterating through the request array
		for (int i=0;i<rSize;i++){
			ifFound=false;// setting ifFound to false for every time it runs in the loop
			for (int j=0; j<cSize ;j++ ){
				//iterating through the cache array and searching if current request is in the cache or not
				if (rArray[i]==cArray[j]){
					// if found add h to the pattern and add 1 to the hit counter 
					output.hitPattern= output.hitPattern+"h";
					output.hitCount=output.hitCount+1;
					ifFound=true;
					break;
				}
			}
			
			// if the current request is not in the cache 
			if (ifFound!=true){
				// adding m to the pattern and adding 1 to the miss counter
				output.hitPattern=output.hitPattern+"m";
				output.missCount=output.missCount+1;
				cArray[lastIndex]= rArray[i]; // replacing the oldest request in the cache with the current request and then updating the lastIndex to point to the next element in the cache sequence
				if (lastIndex == cSize-1 ){
					lastIndex=0;
				}
				else{
					 lastIndex=lastIndex+1;
				}
			}
		}
		// output 
		output.cache = arrayToString(cArray, cSize);
		return output;
		}

	// evictLFD
	// Aim:
	// if a request is not in cache, evict the number whose next request is the latest
	// count number of hit and number of miss, and find the hit-miss pattern; return an object COMP108PagingOutput
	// Input:
	// cArray is an array containing the cache with cSize entries
	// rArray is an array containing the requeset sequence with rSize entries
	static COMP108PagingOutput evictLFD(int[] cArray, int cSize, int[] rArray, int rSize) {
		COMP108PagingOutput output = new COMP108PagingOutput();

		char [] testArray= new char [rSize];
		boolean ifFound=false;
		int [] cache;

		for (int i=0;i<rSize;i++){
			ifFound=false;
			cache=new int [cSize];
			for (int j=0; j<cSize ;j++ ){
				//checking if the current request is in the cache or not
				if (rArray[i]==cArray[j]){
					// if hit add h to the hit pattern and add 1 to the hit count
					output.hitPattern= output.hitPattern+"h";
					output.hitCount=output.hitCount+1;
					ifFound=true;
					break;
				}
			}

			if(ifFound!=true){
				output.hitPattern=output.hitPattern+"m"; // adding m to the hit pattern
				output.missCount=output.missCount+1;// adding 1 to the missCount
				for (int j=0;j<cSize;j++){
					for(int x=0;x<rSize;x++){
						if(cArray[j]==rArray[x]){
							cache[j]=x;
							x= rSize;
						}
						else{
							cache[j]=rSize;
						}

					}
				}

				int numSave=0;
				int numHolder=-1;
				int length=cache.length;
				for (int z=0;z<length;z++){
					if (cache[z]>numHolder){
						numSave=z;
						numHolder=cache[z];
					}
				}
				cArray[numSave]=rArray[i];

		}
	}

		// Sample Inputs and Outputs[image-comments/image2.png]
		output.cache = arrayToString(cArray, cSize);
		return output;
	}

	// DO NOT change this method
	// this will turn the cache into a String
	// Only to be used for output, do not use it to manipulate the cache
	static String arrayToString(int[] array, int size) {
		String outString="";
		
		for (int i=0; i<size; i++) {
			outString += array[i];
			outString += ",";
		}
		return outString;
	}
}

