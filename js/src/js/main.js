require('../style/my.scss')
// require('bootstrap')

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


const startControl = document.getElementById('start')
const stopControl = document.getElementById('stop')
const resetControl = document.getElementById('reset')
const shuffleControl = document.getElementById('shuffle')
const speedControl = document.getElementById('speed-input')

startControl.onclick = start
stopControl.onclick = stop
resetControl.onclick = reset
shuffleControl.onclick = doShuffle
const transformControls = [startControl, resetControl, shuffleControl]

speedControl.oninput = e => {
    speed = e.target.value
    if (player) // In case we are playing
        player.setSpeed(e.target.value)
}

var speed = speedControl.value
const count = 80

const origArray = _.range(count, 0)
// Build simple model for the first display of the array
var model = origArray.map(val => {
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

    const sort = document.querySelector('input[name=sort]:checked')
    const sortFunction = sorts[sort.value]

    playTransformation(sortFunction)
}


function doShuffle() {
    playTransformation(shuffle)
}

function playTransformation(transformation) {
    transformControls.forEach(c => c.setAttribute('disabled', true))
    const array = getArrayFromModel(model)
    var result = transformation(array)
    doPlay(result)
}

function playingDone() {
    transformControls.forEach(c => c.removeAttribute('disabled'))
    player = null
}

function stop() {
    if (player)
        player.stop()
    playingDone()
}

function reset() {
    model = createModel(origArray)
    display.setArrayModel(model)
}

function createModel(array) {
    return array.map(val => {
        return {val: val}
    })
}

function doPlay(result) {
    player = new ActionPlayer(result, speed)
    model = player.getModel()
    display.setArrayModel(model)
    player.play(playingDone)
}



