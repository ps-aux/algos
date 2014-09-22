/*global $ */
/*global Raphael */
//function to be run on page load
/*global start */
/*global alert */

//Classes
/*global VisualArray */
/*global createArray */
/*global runBubbleSort */
/*global delayedBubbleSort */
/*global delayedInsertionSort */
/*global delayedSelectionSort */

var CANVAS_ELEMENT_ID = "paint_canvas";
var WIDTH = 800;
var HEIGHT = 400;

var BCG_GRAY = "#c3c1bf";

//On page load with jQuery
$(start);

var visualArray;

function start() {
	var array = createArray(50, 2, 20);
	array[0] = 30;
	var paper = new Raphael(CANVAS_ELEMENT_ID, WIDTH, HEIGHT);
	visualArray = new VisualArray(array, 20, 80, paper);
}

/**
 * Generates array of the given size with integers from interval [minVal,maxVal].
 */
function createArray(size, maxVal, minVal) {
	var array = [];
	var i, val;
	for (i = 0; i < size; i++) {
		val = Math.floor(Math.random() * (maxVal - minVal + 1)) + minVal;
		array.push(val);
	}
	return array;
}

function runInsertionSort() {
	delayedInsertionSort(visualArray);
}

function runBubbleSort() {
	delayedBubbleSort(visualArray);
}

function runSelectionSort() {
	delayedSelectionSort(visualArray);
}

function VisualArray(array, x, y, paper) {

	var elementWidth = 10;
	var spacing = 5;
	var scaleFactor = 10;
	var elementColor = "red";

	//Add one extra spacing after last element.
	var width = array.length * (spacing + elementWidth) + spacing;
	var height = Math.max.apply(null, array) * scaleFactor;

	//Keep visual representation of elements here
	var elementViews = [];

	function getElementX(index) {
		return x + spacing + index * (elementWidth + spacing);
	}

	/*Code for building the background*/
	var background = paper.rect(x, y, width, height);
	background.attr({
		fill: BCG_GRAY,
		stroke: 0
	});

	/*Code for creation of visual representation of elements*/
	(function() {
		var i, elY, rect, elementHeight;
		for (i = 0; i < array.length; i++) {
			elementHeight = array[i] * scaleFactor;
			elY = y + (height - elementHeight);
			rect = paper.rect(getElementX(i), elY, elementWidth, elementHeight);
			rect.attr({
				fill: elementColor
			});

			//Add to array
			elementViews.push(rect);
		}
	} ());

	function checkBounds(index) {
		if (index >= array.length) {
			throw "Array out of bound " + index;
		}
	}

	this.getVal = function(index) {
		checkBounds(index);
		return array[index];
	};

	this.size = function() {
		return array.length;
	};

	this.getVals = function() {
		return array.slice(0);
	};

	this.swap = function(first, second, duration, callback) {
		checkBounds(first);
		checkBounds(second);
		console.log("swapping");

		var firstView = elementViews[first];
		var secondView = elementViews[second];

		//Swap in element views array.
		elementViews[first] = secondView;
		elementViews[second] = firstView;

		//Swap in data array.
		var tmp = array[first];
		array[first] = array[second];
		array[second] = tmp;

		var firstX = firstView.attr("x");
		var secondX = secondView.attr("x");

		if (duration) {
			//Swap with animation
			var animation1 = Raphael.animation({
				x: secondX
			},
			duration, "linear");
			var animation2 = Raphael.animation({
				x: firstX
			},
			duration, "linear", callback); //The second animation will also activate the callback.
			firstView.animate(animation1);
			secondView.animate(animation2);

		} else {
			//Swap without animation.
			firstView.attr({
				x: secondX
			});
			secondView.attr({
				x: firstX
			});
		}
	};

	function colorElement(index, color) {
		elementViews[index].attr({
			fill: color
		});
	}

	function colorElementsDefault() {
		var i;
		for (i = 0; i < array.length; i++) {
			elementViews[i].attr({
				fill: elementColor
			}); //Set back to normal
		}
	}

	this.hlPrimaryIndex = function(index) {
		checkBounds(index);
		colorElementsDefault();
		colorElement(index, "black");
	};

}

function delayedBubbleSort(vArray) {

	var hasSwapped = false;
	var index = 0;
	var duration = 10;

	function doFlow() {
		if (index === vArray.size() - 1) { //Pass finished.
			if (hasSwapped) { //Still not sorted.
				index = 0; //Start again
				hasSwapped = false;
			} else {
				//We are done.
				return;
			}
		}

		vArray.hlPrimaryIndex(index);

		if (vArray.getVal(index) > vArray.getVal(index + 1)) {
			vArray.swap(index, index + 1);
			hasSwapped = true;
		}
		setTimeout(doFlow, duration);
		index++;
	}

	doFlow();
}

function delayedInsertionSort(vArray) {

	var duration = 5;
	var i = 1;
	var j = 1;

	function doFlow() {
		if (i === vArray.size()) { //Pass finished.
			return;
		}

		if (j === 0 || vArray.getVal(j - 1) < vArray.getVal(j)) {
			//Outer loop has passed, restart inner looping.
			j = ++i;
			setTimeout(doFlow, 0);
		} else {
			vArray.hlPrimaryIndex(j);
			vArray.swap(j, j - 1);
			j--;
			setTimeout(doFlow, duration);
		}
	}

	doFlow();
}

function delayedSelectionSort(vArray) {

	var duration = 50;
    //Values for the very first loop
	var i = 0;
	var j = i + 1;
	var minIndex = i;

	function doFlow() {
		if (i === vArray.size() - 1) { //Last outer loop & algorithm finished
			return;
		}

		if (j === vArray.size()) {//One outer loop has finished
			
			//Swap the found minimam value if necessary
			if (i !== minIndex) {
				vArray.swap(i, minIndex);
			}

			//Restart the inner loop
			j = ++i + 1; //Setting inner control variable
			minIndex = i; //Assumed min value
			setTimeout(doFlow, 0); //Continue loop w/o timeout
		} else {
            vArray.hlPrimaryIndex(j);
			//Body of inner loop
			if (vArray.getVal(j) < vArray.getVal(minIndex)) {
				minIndex = j;
			}
            j++;
			setTimeout(doFlow, duration); //Continue loop with 'duration' timeout
		}
	}

	doFlow();
}

function bubbleSort(vArray) {

	var hasSwapped = false;
	var i;
	do {
		hasSwapped = false;
		for (i = 0; i < vArray.size(); i++) {
			if (vArray.getVal(i) > vArray.getVal(i + 1)) {
				vArray.swap(i, i + 1);
				hasSwapped = true;
			}
		}
	} while (hasSwapped);

}

