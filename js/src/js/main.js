require('../style/my.scss')
// require('bootstrap')

import assert from 'assert'
import _ from 'lodash'
import ActionPlayer from './actionPlayer'
import Display from './display'
import {selectionSort, insertionSort, bubbleSort, mergeSort, shuffle} from  './sorting'

console.log('jajajee')

// Turn off debug
const debug = false
if (!debug)
    console.debug = () => {
    }


document.getElementById('start').onclick = start
document.getElementById('stop').onclick = stop
document.getElementById('shuffle').onclick = doShuffle

const speed = 10
const count = 80

var array = _.range(count, 0)
// Build simple model for the first display of the array
var model = array.map(val => {
    return {val: val}
})

// Show original array
const display = new Display(document.getElementById('sorting-canvas'),
    count)
display.setArrayModel(model)

const sorts = {
    insertion: insertionSort,
    bubble: bubbleSort,
    selection: selectionSort,
    merge: mergeSort
}

var player = null

function getArrayFromModel(model) {
    return model.map(el => el.val);
}

function start() {
    array = getArrayFromModel(model)

    const sort = document.querySelector('input[name=sort]:checked')
    const sortFunction = sorts[sort.value]
    const result = sortFunction(array)

    doPlay(result)
}

function stop() {
    console.log('stopping', player)
    if (player)
        player.stop()
}

function doPlay(result) {
    player = new ActionPlayer(result, speed)
    model = player.getModel()
    display.setArrayModel(model)
    player.play()
}

function doShuffle() {
    array = getArrayFromModel(model)
    var result = shuffle(array)
    doPlay(result)
}

