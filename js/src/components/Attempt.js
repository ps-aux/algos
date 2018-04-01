import React from 'react'
import {range} from "ramda";

const size = 20
const width = 500


const paintNode = (x, y, ctx) => {
    ctx.fillStyle = 'green';
    ctx.fillRect(x, y, size, size);
}

const paintLevel = (lvl, ctx) => {
    ctx.strokeStyle = 'red';
    const y = lvl * size
    // ctx.strokeRect(0, y, width, size)

    const n = Math.pow(2, lvl)
    const space = width - n * size
    const padding = (space / n) / 2

    range(0, n)
        .map(i => i * (size + 2 * padding) + padding)
        .forEach(x => {
            paintNode(x, y, ctx)
        })

}

const height = 10

const paint = canvas => {
    const ctx = canvas.getContext('2d');

    const width = Math.pow(2, height)
    paintLevel(0, ctx)
    paintLevel(1, ctx)
    paintLevel(2, ctx)
    paintLevel(3, ctx)
    paintLevel(4, ctx)

}

const Attempt = () =>
    <canvas id="canvas"
            height={500}
            width={500}
            ref={el => el && paint(el)}/>

export default Attempt