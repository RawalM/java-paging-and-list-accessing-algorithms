// Name: Maadhyam Rawal
//
// Time Complexity and explanation: 
// f denotes initial cabinet size
// n denotes the total number of requests 
// d denotes number of distinct requests
// You can use any of the above notations or define additional notation as you wish.
// 
// appendIfMiss(): O(n×f)
// The algorithm iterates over each request in the requestedItems array, and for each
// request, it may traverse the cache, which has a size proportional to the initial cabinet size f
// freqCount(): O(n×d)
// n the worst case, for each request in the requestedItems array, the algorithm may traverse the 
// cache, which has a size proportional to the number of distinct requests d.

class COMP108Cab {

	public COMP108Node head, tail;
	
	public COMP108Cab() {
		head = null;
		tail = null;
	}

// append to end of list when miss
/**
 * Appends requested items to the end of the cache if they are not already present.
 * 
 * @param requestedItems An array containing the requested items.
 * @param requestSize The number of items in the requestedItems array.
 * @return Output containing hit count, miss count, and comparison count.
 */
public COMP108CabOutput appendIfMiss(int requestedItems[], int requestSize) {
    // Initialize output object to store results
    COMP108CabOutput output = new COMP108CabOutput(requestSize);

    // Iterate through the requested items
    for (int i = 0; i < requestSize; i++) {
        // Initialize variables for the current request
        COMP108Node current = head;
        boolean isFound = false;

        // Traverse the cache to find the requested item
        while (current != null) {
            // Increment comparison count
            output.compare[i]++;

            // Check if the current node contains the requested item
            if (current.data == requestedItems[i]) {
                // If found, set flag, increment hit count, and break out of the loop
                isFound = true;
                output.hitCount++;
                break;
            }

            // Move to the next node in the cache
            current = current.next;
        }

        // If the requested item is not found in the cache
        if (!isFound) {
            // Add the requested item to the cache and increment miss count
            output.missCount++;
            insertTail(new COMP108Node(requestedItems[i]));
        }
    }
	//Sample Inputs and Outputs[image-comments/image3.png]
    // Store the state of the cache (from head to tail) in the output object
    output.cabFromHead = headToTail();
    // Store the state of the cache (from tail to head) in the output object
    output.cabFromTail = tailToHead();

    // Return the output object containing hit count, miss count, and comparison count
    return output;
}


// move the file requested so that order is by non-increasing frequency
/**
 * Performs frequency-based counting for requested items in the cache.
 * 
 * @param requestedItems An array containing the requested items.
 * @param requestSize The number of items in the requestedItems array.
 * @return Output containing hit count, miss count, and comparison count.
 */
public COMP108CabOutput freqCount(int requestedItems[], int requestSize) {
    // Initialize output object to store results
    COMP108CabOutput output = new COMP108CabOutput(requestSize);

    // Initialize variables to track hits, misses, and comparisons
    int hits = 0;
    int misses = 0;
    int[] comparisons = new int[requestSize];

    // Iterate through the requested items
    for (int i = 0; i < requestSize; i++) {
        int numComparisons = 0;
        boolean hit = false;
        COMP108Node current = head;

        // Traverse the cache to find the requested item
        while (current != null) {
            // Increment comparison count
            numComparisons++;

            // Check if the current node contains the requested item
            if (requestedItems[i] == current.data) {
                // If found, update frequency, reorder the cache, and increment hit count
                hit = true;
                current.freq++;
                helperFunction(current);
                hits++;
                // Break out of the loop since the item is found
                break;
            }
            // Move to the next node in the cache
            current = current.next;
        }
    }

    // Update output object with hit count, miss count, and comparison count
    output.hitCount = hits;
    output.missCount = misses;
    output.compare = comparisons;

    // Return the output object containing hit count, miss count, and comparison count
    return output;
}

/**
 * Reorders the linked list based on the frequency of the given node.
 * 
 * @param node The node to be reordered.
 */

public void helperFunction(COMP108Node node) {
    // Remove current node from the linked list
    if (node.prev != null) {
        // Repair linked list next and prev pointers to accommodate for these changes
        node.prev.next = node.next;
        if (node.next != null) {
            node.next.prev = node.prev;
        }

        // If current node is the head
        if (node == head) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            }
        }
        
        // If current node is the tail, update tail pointer value
        if (node == tail) {
            tail = node.prev;
            if (tail != null) {
                tail.next = null;
            }
        }
    }

    // Loop to find position for node in descending frequency order
    COMP108Node index = head;
    while (index != null && (index.freq > node.freq || (index.freq == node.freq && index.data > node.data))) {
        index = index.next;    
    }

    // Swap data and frequency values with the current node
    if (index != null) {
        int tempData = node.data;
        node.data = index.data;
        index.data = tempData;

        int tempFreq = node.freq;
        node.freq = index.freq;
        index.freq = tempFreq;
    }

    // Adds node to position before index, amends all links to node
    node.next = index;
    if (index != null) {
        node.prev = index.prev;
        index.prev = node;
    } else {
        node.prev = tail;
        tail = node;
    }
    if (node.prev != null) {
        node.prev.next = node;
    } else {
        head = node;
    }
}
	
	// DO NOT change this method
	// insert newNode to head of list
	public void insertHead(COMP108Node newNode) {		

		newNode.next = head;
		newNode.prev = null;
		if (head == null)
			tail = newNode;
		else
			head.prev = newNode;
		head = newNode;
	}

	// DO NOT change this method
	// insert newNode to tail of list
	public void insertTail(COMP108Node newNode) {

		newNode.next = null;
		newNode.prev = tail;
		if (tail != null)
			tail.next = newNode;
		else head = newNode;
		tail = newNode;
	}

	// DO NOT change this method
	// delete the node at the head of the linked list
	public COMP108Node deleteHead() {
		COMP108Node curr;

		curr = head;
		if (curr != null) {
			head = head.next;
			if (head == null)
				tail = null;
			else
				head.prev = null;
		}
		return curr;
	}
	
	// DO NOT change this method
	// empty the cabinet by repeatedly removing head from the list
	public void emptyCab() {
		while (head != null)
			deleteHead();
	}


	// DO NOT change this method
	// this will turn the list into a String from head to tail
	// Only to be used for output, do not use it to manipulate the list
	public String headToTail() {
		COMP108Node curr;
		String outString="";
		
		curr = head;
		while (curr != null) {
			outString += curr.data;
			outString += ",";
			curr = curr.next;
		}
		return outString;
	}

	// DO NOT change this method
	// this will turn the list into a String from tail to head
	// Only to be used for output, do not use it to manipulate the list
	public String tailToHead() {
		COMP108Node curr;
		String outString="";
		
		curr = tail;
		while (curr != null) {
			outString += curr.data;
			outString += ",";
			curr = curr.prev;
		}
		return outString;
	}

	// DO NOT change this method
	// this will turn the frequency of the list nodes into a String from head to tail
	// Only to be used for output, do not use it to manipulate the list
	public String headToTailFreq() {
		COMP108Node curr;
		String outString="";
		
		curr = head;
		while (curr != null) {
			outString += curr.freq;
			outString += ",";
			curr = curr.next;
		}
		return outString;
	}

}
