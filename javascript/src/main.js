import assert from 'assert'
import _ from 'lodash'
import ActionPlayer from './actionPlayer'
import Display from './display'
import {selectionSort, insertionSort, bubbleSort, mergeSort, shuffle} from  './sorting'

// Turn off debug
const debug = false
if (!debug)
    console.debug = () => {
    }


document.getElementById('selection-sort').onclick = () => sort('selection')
document.getElementById('insertion-sort').onclick = () => sort('insertion')
document.getElementById('bubble-sort').onclick = () => sort('bubble')
document.getElementById('merge-sort').onclick = () => sort('merge')

document.getElementById('randomize').onclick = randomize

const speed = 10

const orig = _.range(80, 0)
// Build model from sorted (dev purposes)
const model = orig.map(val => {
    return {val: val}
})


// Show original array
const display = new Display(document.getElementById('canvas'), 2)
display.setArrayModel(model)

const sorts = {
    insertion: insertionSort,
    bubble: bubbleSort,
    selection: selectionSort,
    merge: mergeSort
}


function sort(sort) {
    var sortFunction = sorts[sort]

    var result = sortFunction(orig)
    const player = new ActionPlayer(result, speed)
    display.setArrayModel(player.getModel())
    player.play()
}

function randomize() {
    console.log('randomizing', orig)
    var result = shuffle(orig)
    const player = new ActionPlayer(result, speed)
    display.setArrayModel(player.getModel())
    player.play()
}

