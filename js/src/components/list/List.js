import React from 'react'
import * as d3 from 'd3'
import {prop, range} from 'ramda'
import './list.sass'
import {list} from 'src/algo/list'
import {bubbleSort} from 'src/algo/sorting/bubble-sort'

const data = list([
    8, 5, 2, 9, 4, 88, 1
])

const h = 500
const w = 600


const draw = (el, data) => {
    const svg = d3.select(el)

    const u = 10
    const w = 20
    const h = 100
    const m = 10

    const bars = svg.selectAll('rect')
        .data(data)

    bars.enter()
        .append('rect')
        .merge(bars)
        .attrs((d, i) => ({
            x: i * (w + m),
            y: h - d * u,
            height: d * u,
            width: w
        }))

    bars.exit().remove()

}

const mount = el => {
    draw(el, data)
    const {steps} = bubbleSort(data)
    console.log('steps are', steps)

    const i = setInterval(() => {
        const s = steps.shift()
        if (!s) {
            clearInterval(i)
            return
        }
        draw(el, s)
    }, 500)
}


const List = () => {
    return <div className="list">
        This is list
        <svg ref={el => el && mount(el)}
             height={h} width={w}/>
    </div>
}

export default List